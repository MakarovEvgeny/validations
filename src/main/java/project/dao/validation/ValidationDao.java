package project.dao.validation;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import project.dao.BaseVersionableModelDao;
import project.dao.ConcurrentModificationException;
import project.dao.FindAbility;
import project.dao.SearchParamsProcessor;
import project.model.Change;
import project.model.entity.Entity;
import project.model.message.Message;
import project.model.operation.Operation;
import project.model.query.SearchParams;
import project.model.validation.Severity;
import project.model.validation.Validation;
import project.model.validation.ValidationDto;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singletonMap;
import static project.dao.RequestRegistry.lookup;
import static project.dao.SearchParamsProcessor.process;

@Repository
public class ValidationDao extends BaseVersionableModelDao<Validation> implements FindAbility<ValidationDto>, ValidationValidatorDao {

    private RowMapper<Validation> mapper = (rs, rowNum) -> {
        Message message = new Message(rs.getString("m_id"), rs.getString("m_text"), rs.getInt("m_version"), rs.getString("m_commentary"));
        Severity severity = Severity.resolveById(rs.getInt("severityId"));
        return new Validation(rs.getString("id"), severity, message, rs.getString("description"), rs.getInt("version"), rs.getString("commentary"));
    };

    private RowMapper<ValidationDto> dtoMapper = (rs, rowNum) -> {
        ValidationDto dto = new ValidationDto();
        dto.id = rs.getString("id");
        dto.messageId = rs.getString("messageId");
        dto.messageText = rs.getString("messageText");
        dto.description= rs.getString("description");
        dto.version = rs.getInt("version");
        dto.commentary = rs.getString("commentary");
        dto.severityId = rs.getString("severityId");
        dto.severityName = rs.getString("severityName");
        dto.entityNames = rs.getString("entityNames");
        dto.operationNames = rs.getString("operationNames");

        return dto;
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

        createEntities(validation);
        createOperations(validation);

        createHistory(validation, false);
    }

    @Override
    public void update(Validation validation) {
        deleteEntities(validation);
        deleteOperations(validation);

        createEntities(validation);
        createOperations(validation);

        int rowsAffected = jdbc.update(lookup("validation/UpdateValidation"), prepareParams(validation));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }

        createHistory(validation, false);
    }

    private void createOperations(Validation validation) {
        SqlParameterSource[] operationParams = validation.getOperations().stream().map(operation -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("id", validation.getId());
            source.addValue("operationId", operation.getId());
            return source;
        }).toArray(SqlParameterSource[]::new);
        jdbc.batchUpdate(lookup("validation/CreateValidationOperations"), operationParams);
    }

    private void createEntities(Validation validation) {
        SqlParameterSource[] entityParams = validation.getEntities().stream().map(entity -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("id", validation.getId());
            source.addValue("entityId", entity.getId());
            return source;
        }).toArray(SqlParameterSource[]::new);
        jdbc.batchUpdate(lookup("validation/CreateValidationEntities"), entityParams);
    }

    private void deleteOperations(Validation validation) {
        jdbc.update(lookup("validation/DeleteValidationOperations"), singletonMap("id", validation.getId()));
    }

    private void deleteEntities(Validation validation) {
        jdbc.update(lookup("validation/DeleteValidationEntities"), singletonMap("id", validation.getId()));
    }


    private void createHistory(Validation validation, boolean isForDelete) {

        MapSqlParameterSource params = new MapSqlParameterSource(isForDelete ? prepareHistoricalParamsForDelete(validation) : prepareHistoricalParams(validation));

        GeneratedKeyHolder idHolder = new GeneratedKeyHolder();
        jdbc.update(lookup("validation/CreateValidationHistory"), params, idHolder, new String[]{"validation_version_id"});

        int validationVersionId = idHolder.getKey().intValue();

        createEntitiesHistory(validationVersionId, validation.getEntities());
        createOperationsHistory(validationVersionId, validation.getOperations());
    }

    private void createEntitiesHistory(int validationVersionId, Set<Entity> entities) {
        SqlParameterSource[] entityParams = entities.stream().map(entity -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("validationVersionId", validationVersionId);
            params.addValue("entityId", entity.getId());
            return params;
        }).toArray(SqlParameterSource[]::new);

        jdbc.batchUpdate(lookup("validation/CreateValidationEntitiesHistory"), entityParams);
    }

    private void createOperationsHistory(int validationVersionId, Set<Operation> operations) {
        SqlParameterSource[] entityParams = operations.stream().map(operation -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("validationVersionId", validationVersionId);
            params.addValue("operationId", operation.getId());
            return params;
        }).toArray(SqlParameterSource[]::new);

        jdbc.batchUpdate(lookup("validation/CreateValidationOperationsHistory"), entityParams);
    }


    @Override
    protected Map<String, Object> prepareParams(Validation validation) {
        Map<String, Object> params = super.prepareParams(validation);
        params.put("severityId", validation.getSeverity().getId());
        params.put("messageId", validation.getMessage().getId());
        params.put("description", validation.getDescription());
        return params;
    }

    private Map<String, Object> prepareHistoricalParamsForDelete(Validation validation) {
        Map<String, Object> params = super.prepareHistoricalParams(validation);
        params.put("messageId", null);
        params.put("description", null);
        return params;
    }


    @Override
    public void remove(Validation validation) {
        deleteEntities(validation);
        deleteOperations(validation);

        int rowsAffected = jdbc.update(lookup("validation/DeleteValidation"), prepareParams(validation));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }

        createHistory(validation, true);
    }

    public List<ValidationDto> find(SearchParams searchParams) {
        SearchParamsProcessor.ProcessResult result = process(lookup("validation/FindValidation"), searchParams);
        return jdbc.query(result.getResultQuery(), result.getParams(), dtoMapper);
    }


    private Set<Entity> loadEntities(String validationId) {
        List<Entity> data = jdbc.query(lookup("validation/LoadValidationEntities"), singletonMap("id", validationId), entityMapper);
        return new HashSet<>(data);
    }

    private Set<Operation> loadOperations(String validationId) {
        List<Operation> data = jdbc.query(lookup("validation/LoadValidationOperations"), singletonMap("id", validationId), operationMapper);
        return new HashSet<>(data);
    }

    @Override
    public boolean alreadyExists(String id) {
        return jdbc.queryForObject(lookup("validation/AlreadyExists"), singletonMap("id", id), Boolean.class);
    }

    @Override
    public boolean messageExists(String messageId) {
        return jdbc.queryForObject(lookup("validation/MessageExists"), singletonMap("id", messageId), Boolean.class);
    }

    public List<Change> getChanges(String id) {
        return jdbc.query(lookup("validation/LoadChanges"), singletonMap("id", id), changeMapper);
    }

}
