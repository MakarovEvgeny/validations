package project.dao.entity;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.dao.BaseVersionAwareModelDao;
import project.dao.ConcurrentModificationException;
import project.model.entity.Entity;
import project.model.query.SearchParams;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static project.dao.RequestRegistry.lookup;

@Repository
public class EntityDao extends BaseVersionAwareModelDao<Entity> {

    private RowMapper<Entity> mapper = (rs, rowNum) -> new Entity(rs.getString("id"), rs.getString("name"), rs.getString("description"), rs.getInt("version"), rs.getString("commentary"));


    public Entity load(String entityId) {
        return jdbc.queryForObject(lookup("entity/LoadEntity"), singletonMap("id", entityId), mapper);
    }

    public void create(Entity entity) {
        jdbc.update(lookup("entity/CreateEntity"), prepareParams(entity));
        createHistory(entity);
    }

    private void createHistory(Entity entity) {
        jdbc.update(lookup("entity/CreateEntityHistory"), prepareHistoricalParams(entity));
    }


    public void update(Entity entity) {
        int rowsAffected = jdbc.update(lookup("entity/UpdateEntity"), prepareParams(entity));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }
        createHistory(entity);
    }


    public void remove(Entity entity) {
        int rowsAffected = jdbc.update(lookup("entity/DeleteEntity"), prepareParams(entity));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }

        jdbc.update(lookup("entity/CreateEntityHistory"), prepareHistoricalParamsForRemove(entity));
    }

    public List<Entity> find(SearchParams searchParams) {
        return jdbc.query(lookup("entity/FindEntity"), mapper);
    }


    @Override
    protected Map<String, Object> prepareParams(Entity entity) {
        Map<String, Object> params = super.prepareParams(entity);
        params.put("name", entity.getName());
        params.put("description", entity.getDescription());
        return params;
    }

    private Map<String, Object> prepareHistoricalParamsForRemove(Entity model) {
        Map<String, Object> params = prepareHistoricalParams(model);
        params.put("name", null);
        params.put("description", null);
        return params;
    }

}
