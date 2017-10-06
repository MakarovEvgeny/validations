package project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project.model.Entity;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

@Repository
public class EntityDao {

    @Autowired
    private DataSource ds;

    private NamedParameterJdbcTemplate jdbc;

    private RowMapper<Entity> mapper = (rs, rowNum) -> new Entity(rs.getString("entity_id"), rs.getString("name"), rs.getString("description"), rs.getInt("version"));

    @PostConstruct
    public void init() {
        jdbc = new NamedParameterJdbcTemplate(ds);
    }

    public Entity load(String entityId) {
        return jdbc.queryForObject("select * from entity where entity_id = :id", singletonMap("id", entityId), mapper);
    }

    public void create(Entity entity) {
        jdbc.update("insert into entity(entity_id, name, description) values (:id, :name, :description)", prepareParams(entity));
    }

    public void update(Entity entity) {
        int rowsAffected = jdbc.update("update entity set name = :name, description = :description, version = version + 1 where entity_id = :id and version = :version", prepareParams(entity));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }
    }

    private Map<String, Object> prepareParams(Entity entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", entity.getId());
        params.put("name", entity.getName());
        params.put("description", entity.getDescription());
        params.put("version", entity.getVersion());

        return params;
    }

    public void remove(Entity entity) {
        int rowsAffected = jdbc.update("delete from entity WHERE entity_id = :id and version = :version", prepareParams(entity));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }
    }

    public List<Entity> find() {
        return jdbc.query("select * from entity", mapper);
    }


}
