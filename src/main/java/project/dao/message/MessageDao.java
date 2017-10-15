package project.dao.message;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.dao.BaseVersionAwareModelDao;
import project.dao.ConcurrentModificationException;
import project.model.message.Message;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

@Repository
public class MessageDao extends BaseVersionAwareModelDao<Message> {

    private RowMapper<Message> mapper = (rs, rowNum) -> new Message(rs.getString("message_id"), rs.getString("text"), rs.getInt("version"), rs.getString("commentary"));

    @Override
    public Message load(String messageId) {
        return jdbc.queryForObject("select * from message where message_id = :id", singletonMap("id", messageId), mapper);
    }

    @Override
    public void create(Message message) {
        jdbc.update("insert into message(message_id, text, commentary) values (:id, :text, :commentary)", prepareParams(message));
        createHistory(message);
    }

    @Override
    public void update(Message message) {
        int affectedRows = jdbc.update("update message set text = :text, version = version + 1, commentary = :commentary where message_id = :id and version = :version", prepareParams(message));
        if (affectedRows == 0) {
            throw new ConcurrentModificationException();
        }
        createHistory(message);
    }

    @Override
    public void remove(Message message) {
        int affectedRows = jdbc.update("delete from message where message_id = :id and version = :version", prepareParams(message));
        if (affectedRows == 0) {
            throw new ConcurrentModificationException();
        }
        createHistoryForRemoval(message);
    }

    @Override
    public List<Message> find() {
        return jdbc.query("select * from message", mapper);
    }

    private void createHistory(Message message) {
        jdbc.update("insert into message_h(message_id, text, version, date, commentary) values (:id, :text, :version, :date, :commentary)", prepareHistoricalParams(message));
    }

    private void createHistoryForRemoval(Message message) {
        jdbc.update("insert into message_h(message_id, version, date, commentary) values (:id, :version, :date, :commentary)", prepareHistoricalParams(message));
    }

    @Override
    protected Map<String, Object> prepareParams(Message message) {
        Map<String, Object> params = super.prepareParams(message);
        params.put("text", message.getText());
        return params;
    }

}
