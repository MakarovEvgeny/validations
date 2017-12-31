package project.dao.entity;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.dao.BaseVersionableModelDao;
import project.dao.ConcurrentModificationException;
import project.dao.FindAbility;
import project.dao.SearchParamsProcessor.ProcessResult;
import project.model.Change;
import project.model.entity.Entity;
import project.model.query.SearchParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static project.dao.RequestRegistry.lookup;
import static project.dao.SearchParamsProcessor.process;

@Repository
public class EntityDao extends BaseVersionableModelDao<Entity> implements FindAbility<Entity>, EntityValidatorDao {

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
        ProcessResult processResult = process(lookup("entity/FindEntity"), searchParams);
        return jdbc.query(processResult.getResultQuery(), processResult.getParams(), mapper);
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

    /** Проверка существования записи в БД с указанным id. */
    public boolean alreadyExists(String id) {
        return jdbc.queryForObject(lookup("entity/AlreadyExists"), singletonMap("id", id), Boolean.class);
    }

    /** Проверка существования записи в БД с указанным наименованием. */
    public boolean nameAlreadyExists(String id, String name) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);

        return jdbc.queryForObject(lookup("entity/NameAlreadyExists"), params, Boolean.class);
    }

    /** Проверка что на удаляемую запись ссылаются из других таблиц. */
    public boolean isUsed(String id) {
        return jdbc.queryForObject(lookup("entity/IsUsed"), singletonMap("id", id), Boolean.class);
    }

    public List<Change> getChanges(String id) {
        return jdbc.query(lookup("entity/LoadChanges"), singletonMap("id", id), changeMapper);
    }

    @Override
    public Entity loadVersion(int versionId) {
        return jdbc.queryForObject(lookup("entity/LoadEntityVersion"), singletonMap("id", versionId), mapper);
    }

}
