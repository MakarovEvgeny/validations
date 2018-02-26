package project.dao.validation;

import org.springframework.jdbc.core.ResultSetExtractor;
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
import project.model.validation.ValidationEntity;
import project.model.validation.ValidationExportRow;

import java.util.*;
import java.util.stream.Collectors;

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

    private RowMapper<ValidationExportRow> exportMapper = (rs, rowNum) ->
        new ValidationExportRow(rs.getString("code"), rs.getString("severity"), rs.getString("messageCode"), rs.getString("entities"), rs.getString("operations"), rs.getString("description"));

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

    private RowMapper<Entity> entityMapper = (rs, rowNum) -> new Entity(rs.getString("e_id"), rs.getString("e_name"), rs.getString("e_description"), rs.getInt("e_version"), rs.getString("e_commentary"));

    private RowMapper<Operation> operationMapper = (rs, rowNum) -> new Operation(rs.getString("o_id"), rs.getString("o_name"), rs.getString("o_description"), rs.getInt("o_version"), rs.getString("o_commentary"));

    private ResultSetExtractor<List<ValidationEntity>> rse = rs -> {
        Map<Entity, Set<Operation>> map = new HashMap<>();

        while (rs.next()) {
            Entity entity = entityMapper.mapRow(rs, 0);
            Operation operation = operationMapper.mapRow(rs, 0);

            if (map.containsKey(entity)) {
                map.get(entity).add(operation);
            } else {
                Set<Operation> operations = new HashSet<>();
                operations.add(operation);
                map.put(entity, operations);
            }
        }

        List<ValidationEntity> result = new ArrayList<>();

        for (Map.Entry<Entity, Set<Operation>> entry : map.entrySet()) {
            result.add(new ValidationEntity(entry.getKey(), entry.getValue()));
        }

        return result;
    };

    @Override
    public Validation load(String validationId) {
        Validation validation = jdbc.queryForObject(lookup("validation/LoadValidation"), singletonMap("id", validationId), mapper);
        validation.setValidationEntities(loadValidationEntities(validationId));
        return validation;
    }

    @Override
    public void create(Validation validation) {
        jdbc.update(lookup("validation/CreateValidation"), prepareParams(validation));

        createEntityOperations(validation);

        createHistory(validation);
    }

    @Override
    public void update(Validation validation) {
        deleteEntityOperations(validation);

        createEntityOperations(validation);

        int rowsAffected = jdbc.update(lookup("validation/UpdateValidation"), prepareParams(validation));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }

        createHistory(validation);
    }

    private void createEntityOperations(Validation validation) {

        List<SqlParameterSource> params = new ArrayList<>();

        validation.getValidationEntities().forEach(validationEntity -> {
            List<MapSqlParameterSource> validationEntityResult = validationEntity.getOperations().stream().map(operation -> {
                MapSqlParameterSource source = new MapSqlParameterSource();
                source.addValue("id", validation.getId());
                source.addValue("entityId", validationEntity.getEntity().getId());
                source.addValue("operationId", operation.getId());
                return source;
            }).collect(Collectors.toList());
            params.addAll(validationEntityResult);
        });

        jdbc.batchUpdate(lookup("validation/CreateValidationEntityOperation"), params.toArray(new SqlParameterSource[]{}));
    }

    private void deleteEntityOperations(Validation validation) {
        jdbc.update(lookup("validation/DeleteValidationEntityOperations"), singletonMap("id", validation.getId()));
    }


    private void createHistory(Validation validation) {

        MapSqlParameterSource params = new MapSqlParameterSource(prepareHistoricalParams(validation));

        GeneratedKeyHolder idHolder = new GeneratedKeyHolder();
        jdbc.update(lookup("validation/CreateValidationHistory"), params, idHolder, new String[]{"validation_version_id"});

        int validationVersionId = idHolder.getKey().intValue();

        createEntityOperationsHistory(validationVersionId, validation.getValidationEntities());
    }

    private void createEntityOperationsHistory(int validationVersionId, Set<ValidationEntity> entities) {
        List<SqlParameterSource> params = new ArrayList<>();

        entities.forEach(validationEntity -> {
            validationEntity.getOperations().forEach(operation -> {
                MapSqlParameterSource source = new MapSqlParameterSource();
                source.addValue("validationVersionId", validationVersionId);
                source.addValue("entityId", validationEntity.getEntity().getId());
                source.addValue("operationId", operation.getId());
                params.add(source);
            });
        });

        jdbc.batchUpdate(lookup("validation/CreateValidationEntitiesHistory"), params.toArray(new SqlParameterSource[]{}));
    }


    @Override
    protected Map<String, Object> prepareParams(Validation validation) {
        Map<String, Object> params = super.prepareParams(validation);
        params.put("severityId", validation.getSeverity().getId());
        params.put("messageId", validation.getMessage().getId());
        params.put("description", validation.getDescription());
        return params;
    }



    @Override
    public void remove(Validation validation) {
        deleteEntityOperations(validation);

        int rowsAffected = jdbc.update(lookup("validation/DeleteValidation"), prepareParams(validation));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }

        createHistory(validation);
    }

    public List<ValidationDto> find(SearchParams searchParams) {
        SearchParamsProcessor.ProcessResult result = process(lookup("validation/FindValidation"), searchParams);
        return jdbc.query(result.getResultQuery(), result.getParams(), dtoMapper);
    }


    private Set<ValidationEntity> loadValidationEntities(String validationId) {
        List<ValidationEntity> data = jdbc.query(lookup("validation/LoadValidationEntities"), singletonMap("id", validationId), rse);
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

    @Override
    public Validation loadVersion(int versionId) {
        Validation validation = jdbc.queryForObject(lookup("validation/LoadValidationVersion"), singletonMap("id", versionId), mapper);
        validation.setValidationEntities(loadEntitiesVersions(versionId));
        return validation;
    }

    private Set<ValidationEntity> loadEntitiesVersions(int validationVersionId) {
        List<ValidationEntity> data = jdbc.query(lookup("validation/LoadValidationEntitiesVersions"), singletonMap("id", validationVersionId), rse);
        return new HashSet<>(data);
    }

    public List<ValidationExportRow> exportValidations() {
        return jdbc.query(lookup("validation/ExportValidations"), exportMapper);
    }
}
