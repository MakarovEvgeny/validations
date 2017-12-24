package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dao.entity.EntityDao;
import project.model.Change;
import project.model.entity.Entity;
import project.model.query.SearchParams;

import java.util.List;

@Service
@Transactional
public class EntityServiceImpl implements EntityService {

    @Autowired
    private EntityDao dao;

    @Override
    public Entity load(String entityId) {
        return dao.load(entityId);
    }

    @Override
    public void create(Entity entity) {
        dao.create(entity);
    }

    @Override
    public void update(Entity entity) {
        dao.update(entity);
    }

    @Override
    public void remove(Entity entity) {
        dao.remove(entity);
    }

    @Override
    public List<Entity> find(SearchParams searchParams) {
        return dao.find(searchParams);
    }

    @Override
    public List<Change> getChanges(String entityId) {
        return dao.getChanges(entityId);
    }

}
