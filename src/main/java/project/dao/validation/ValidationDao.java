package project.dao.validation;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import project.dao.BaseVersionAwareModelDao;
import project.model.entity.Entity;
import project.model.message.Message;
import project.model.operation.Operation;
import project.model.validation.Validation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singletonMap;
import static project.dao.RequestRegistry.lookup;

@Repository
public class ValidationDao extends BaseVersionAwareModelDao<Validation> {

    private RowMapper<Validation> mapper = (rs, rowNum) -> {
        Message message = new Message(rs.getString("m_id"), rs.getString("m_text"), rs.getInt("m_version"), rs.getString("m_commentary"));
        return new Validation(rs.getString("id"), message, rs.getString("description"), rs.getInt("version"), rs.getString("commentary"));
    };

    private RowMapper<Entity> entityMapper = (rs, rowNum) -> new Entity(rs.getString("id"), rs.getString("name"), rs.getString("description"), rs.getInt("version"), rs.getString("commentary"));

    private RowMapper<Operation> operationMapper = (rs, rowNum) -> new Operation(rs.getString("id"), rs.getString("name"), rs.getString("description"), rs.getInt("version"), rs.getString("commentary"));



    @Override
    public Validation load(String validationId) {
        Validation validation = jdbc.queryForObject(lookup("validation/LoadValidation"), singletonMap("id", validationId), mapper);
        validation.setEntities(loadEntities(validationId));
        validation.setOperations(loadOperations(validationId));
        return validation;
    }

    @Override
    public void create(Validation validation) {
        jdbc.update(lookup("validation/CreateValidation"), prepareParams(validation));

        SqlParameterSource[] entityParams = validation.getEntities().stream().map(entity -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("id", validation.getId());
            source.addValue("entityId", entity.getId());
            return source;
        }).toArray(SqlParameterSource[]::new);
        jdbc.batchUpdate(lookup("validation/CreateValidationEntities"), entityParams);

        SqlParameterSource[] operationParams = validation.getOperations().stream().map(operation -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("id", validation.getId());
            source.addValue("operationId", operation.getId());
            return source;
        }).toArray(SqlParameterSource[]::new);
        jdbc.batchUpdate(lookup("validation/CreateValidationOperations"), operationParams);
    }

    @Override
    protected Map<String, Object> prepareParams(Validation validation) {
        Map<String, Object> params = super.prepareParams(validation);
        params.put("messageId", validation.getMessage().getId());
        params.put("description", validation.getDescription());
        return params;
    }

    @Override
    public void update(Validation validation) {

    }

    @Override
    public void remove(Validation validation) {

    }

    @Override
    public List<Validation> find() {
        return null;
    }


    private Set<Entity> loadEntities(String validationId) {
        List<Entity> data = jdbc.query(lookup("validation/LoadValidationEntities"), singletonMap("id", validationId), entityMapper);
        return new HashSet<>(data);
    }

    private Set<Operation> loadOperations(String validationId) {
        List<Operation> data = jdbc.query(lookup("validation/LoadValidationOperations"), singletonMap("id", validationId), operationMapper);
        return new HashSet<>(data);
    }

}
