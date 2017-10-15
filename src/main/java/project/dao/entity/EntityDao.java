package project.dao.entity;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.dao.BaseVersionAwareModelDao;
import project.dao.ConcurrentModificationException;
import project.model.entity.Entity;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

@Repository
public class EntityDao extends BaseVersionAwareModelDao<Entity> {

    private RowMapper<Entity> mapper = (rs, rowNum) -> new Entity(rs.getString("entity_id"), rs.getString("name"), rs.getString("description"), rs.getInt("version"), rs.getString("commentary"));


    public Entity load(String entityId) {
        return jdbc.queryForObject("select * from entity where entity_id = :id", singletonMap("id", entityId), mapper);
    }

    public void create(Entity entity) {
        jdbc.update("insert into entity(entity_id, name, description, commentary) values (:id, :name, :description, :commentary)", prepareParams(entity));
        createHistory(entity);
    }

    private void createHistory(Entity entity) {
        jdbc.update("insert into entity_h(date, entity_id, name, description, version, commentary) values (:date, :id, :name, :description, :version, :commentary)", prepareHistoricalParams(entity));
    }

    private void createHistoryForRemoval(Entity entity) {
        jdbc.update("insert into entity_h(date, entity_id, version, commentary) values (:date, :id, :version, :commentary)", prepareHistoricalParams(entity));
    }

    public void update(Entity entity) {
        int rowsAffected = jdbc.update("update entity set name = :name, description = :description, version = version + 1, commentary = :commentary where entity_id = :id and version = :version", prepareParams(entity));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }
        createHistory(entity);
    }


    public void remove(Entity entity) {
        int rowsAffected = jdbc.update("delete from entity WHERE entity_id = :id and version = :version", prepareParams(entity));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }
        createHistoryForRemoval(entity);
    }

    public List<Entity> find() {
        return jdbc.query("select * from entity", mapper);
    }


    @Override
    protected Map<String, Object> prepareParams(Entity entity) {
        Map<String, Object> params = super.prepareParams(entity);
        params.put("name", entity.getName());
        params.put("description", entity.getDescription());
        return params;
    }

}
