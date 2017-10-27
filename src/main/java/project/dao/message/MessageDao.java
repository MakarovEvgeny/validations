package project.dao.message;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.dao.BaseVersionAwareModelDao;
import project.dao.ConcurrentModificationException;
import project.model.message.Message;
import project.model.query.SearchParams;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static project.dao.RequestRegistry.lookup;

@Repository
public class MessageDao extends BaseVersionAwareModelDao<Message> {

    private RowMapper<Message> mapper = (rs, rowNum) -> new Message(rs.getString("id"), rs.getString("text"), rs.getInt("version"), rs.getString("commentary"));

    @Override
    public Message load(String messageId) {
        return jdbc.queryForObject(lookup("message/LoadMessage"), singletonMap("id", messageId), mapper);
    }

    @Override
    public void create(Message message) {
        jdbc.update(lookup("message/CreateMessage"), prepareParams(message));
        createHistory(message);
    }

    @Override
    public void update(Message message) {
        int affectedRows = jdbc.update(lookup("message/UpdateMessage"), prepareParams(message));
        if (affectedRows == 0) {
            throw new ConcurrentModificationException();
        }
        createHistory(message);
    }

    @Override
    public void remove(Message message) {
        int affectedRows = jdbc.update(lookup("message/DeleteMessage"), prepareParams(message));
        if (affectedRows == 0) {
            throw new ConcurrentModificationException();
        }
        jdbc.update(lookup("message/CreateMessageHistory"), prepareHistoricalParamsForRemove(message));
    }

    @Override
    public List<Message> find(SearchParams searchParams) {
        return jdbc.query(lookup("message/FindMessage"), mapper);
    }

    private void createHistory(Message message) {
        jdbc.update(lookup("message/CreateMessageHistory"), prepareHistoricalParams(message));
    }

    @Override
    protected Map<String, Object> prepareParams(Message message) {
        Map<String, Object> params = super.prepareParams(message);
        params.put("text", message.getText());
        return params;
    }

    private Map<String, Object> prepareHistoricalParamsForRemove(Message model) {
        Map<String, Object> params = super.prepareHistoricalParams(model);
        params.put("text", null);
        return params;
    }
}
