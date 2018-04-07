package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dao.message.MessageDao;
import project.model.Change;
import project.model.message.Message;
import project.model.query.SearchParams;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageDao dao;

    @Autowired
    public MessageServiceImpl(MessageDao dao) {
        this.dao = dao;
    }

    @Override
    public Message load(String messageId) {
        return dao.load(messageId);
    }

    @Override
    public void create(Message message) {
        dao.create(message);
    }

    @Override
    public void update(Message message) {
        dao.update(message);
    }

    @Override
    public void remove(Message message) {
        dao.remove(message);
    }

    @Override
    public List<Message> find(SearchParams searchParams) {
        return dao.find(searchParams);
    }

    @Override
    public List<Change> getChanges(String messageId) {
        return dao.getChanges(messageId);
    }

    @Override
    public Message loadVersion(int versionId) {
        return dao.loadVersion(versionId);
    }

}
