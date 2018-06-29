package project.dao.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

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
import project.dao.tag.TagDao;
import project.model.Change;
import project.model.entity.Entity;
import project.model.message.Message;
import project.model.operation.Operation;
import project.model.query.SearchParams;
import project.model.tag.Tag;
import project.model.validation.Severity;
import project.model.validation.Validation;
import project.model.validation.ValidationDto;
import project.model.validation.ValidationEntity;
import project.model.validation.ValidationExportRow;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.springframework.dao.support.DataAccessUtils.requiredSingleResult;
import static project.dao.RequestRegistry.lookup;
import static project.dao.SearchParamsProcessor.process;

@Repository
public class ValidationDao extends BaseVersionableModelDao<Validation> implements FindAbility<ValidationDto>, ValidationValidatorDao {

    private RowMapper<Validation> mapper = (rs, rowNum) -> {
        Message message = new Message(rs.getString("m_id"), rs.getString("m_text"), rs.getInt("m_version"), rs.getString("m_commentary"));
        Severity severity = Severity.resolveById(rs.getInt("severityId"));
        return new Validation(rs.getString("id"), severity, message, rs.getString("description"), rs.getInt("version"), rs.getString("commentary"));
    };

    private ResultSetExtractor<Map<String, Validation>> validationsByIdExtractor = rs -> {
        Map<String, Validation> result = new HashMap<>();
        while (rs.next()) {
            Validation validation = mapper.mapRow(rs, rs.getRow());
            result.put(validation.getId(), validation);
        }
        return result;
    };

    private ResultSetExtractor<Map<String, Set<Tag>>> tagsByValidationIdExtractor = rs -> {
        Map<String, Set<Tag>> result = new HashMap<>();
        while (rs.next()) {
            String validationId = rs.getString("v_id");
            Tag tag = TagDao.TAG_MAPPER.mapRow(rs, rs.getRow());

            if (result.containsKey(validationId)) {
                result.get(validationId).add(tag);
            } else {
                Set<Tag> tags = new HashSet<>();
                result.put(validationId, tags);
                tags.add(tag);
            }
        }
        return result;
    };

    private RowMapper<ValidationExportRow> exportMapper = (rs, rowNum) ->
        new ValidationExportRow(rs.getString("code"), rs.getString("severity"), rs.getString("messageCode"), rs.getString("entities"), rs.getString("operations"), rs.getString("description"), rs.getString("tags"));

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
        dto.tagNames = rs.getString("tagNames");

        return dto;
    };

    private RowMapper<Entity> entityMapper = (rs, rowNum) -> new Entity(rs.getString("e_id"), rs.getString("e_name"), rs.getString("e_description"), rs.getInt("e_version"), rs.getString("e_commentary"));

    private RowMapper<Operation> operationMapper = (rs, rowNum) -> new Operation(rs.getString("o_id"), rs.getString("o_name"), rs.getString("o_description"), rs.getInt("o_version"), rs.getString("o_commentary"));

    private ResultSetExtractor<Map<String, Set<ValidationEntity>>> rse = rs -> {
        Map<String, Map<Entity, Set<Operation>>> entityByValidationId = new HashMap<>();

        while (rs.next()) {
            String validationId = rs.getString("v_id");
            Entity entity = entityMapper.mapRow(rs, 0);
            Operation operation = operationMapper.mapRow(rs, 0);

            Map<Entity, Set<Operation>> map;
            if (entityByValidationId.containsKey(validationId)) {
                map = entityByValidationId.get(validationId);
            } else {
                map = new HashMap<>();
                entityByValidationId.put(validationId, map);
            }

            if (map.containsKey(entity)) {
                map.get(entity).add(operation);
            } else {
                Set<Operation> operations = new HashSet<>();
                operations.add(operation);
                map.put(entity, operations);
            }
        }

        Map<String, Set<ValidationEntity>> result = new HashMap<>();
        for (String validationId : entityByValidationId.keySet()) {
            Set<ValidationEntity> entities = new HashSet<>();
            result.put(validationId, entities);

            Map<Entity, Set<Operation>> map = entityByValidationId.get(validationId);
            for (Map.Entry<Entity, Set<Operation>> entry : map.entrySet()) {
                entities.add(new ValidationEntity(entry.getKey(), entry.getValue()));
            }
        }

        return result;
    };

    public ValidationDao(DataSource ds) {
        super(ds);
    }

    @Override
    public Validation load(String validationId) {
        return requiredSingleResult(load(singletonList(validationId)));
    }

    public List<Validation> load(List<String> validationIds) {
        Map<String, Validation> validations =
                jdbc.query(lookup("validation/LoadValidation"), singletonMap("ids", validationIds), validationsByIdExtractor);

        Map<String, Set<ValidationEntity>> entitiesByValidationId = loadValidationEntities(validationIds);
        Map<String, Set<Tag>> tags = loadTags(validationIds);
        for (String validationId : validations.keySet()) {
            Validation validation = validations.get(validationId);
            validation.setValidationEntities(entitiesByValidationId.get(validationId));
            validation.setTags(tags.get(validationId));
        }

        return new ArrayList<>(validations.values());
    }

    @Override
    public void create(Validation validation) {
        jdbc.update(lookup("validation/CreateValidation"), prepareParams(validation));

        createEntityOperations(singletonList(validation));
        createTags(singletonList(validation));

        createHistory(validation);
    }

    @Override
    public void update(Validation validation) {
        update(singletonList(validation));
    }

    public void update(List<Validation> validations) {
        deleteEntityOperations(validations);
        deleteTags(validations);

        createEntityOperations(validations);
        createTags(validations);

        List<SqlParameterSource> params = validations.stream()
                .map(this::prepareParams)
                .map(MapSqlParameterSource::new)
                .collect(Collectors.toList());

        int[] rowsAffected = jdbc.batchUpdate(lookup("validation/UpdateValidation"), params.toArray(new SqlParameterSource[]{}));
        if (Arrays.stream(rowsAffected).filter(value -> value == 0).count() > 0) {
            throw new ConcurrentModificationException();
        }

        //TODO убрать цикл
        for (Validation validation : validations) {
            createHistory(validation);
        }
    }

    private void createEntityOperations(List<Validation> validations) {
        List<SqlParameterSource> params = new ArrayList<>();

        for (Validation validation : validations) {
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
        }

        jdbc.batchUpdate(lookup("validation/CreateValidationEntityOperation"), params.toArray(new SqlParameterSource[]{}));
    }

    private void createTags(List<Validation> validations) {
        List<SqlParameterSource> params = new ArrayList<>();
        for (Validation validation : validations) {
            params.addAll(validation.getTags().stream().map(tag -> {
                MapSqlParameterSource source = new MapSqlParameterSource();
                source.addValue("id", validation.getId());
                source.addValue("tagId", tag.getId());
                return source;
            }).collect(Collectors.toList()));
        }

        jdbc.batchUpdate(lookup("validation/CreateValidationTag"), params.toArray(new SqlParameterSource[]{}));
    }

    private void deleteEntityOperations(List<Validation> validations) {
        jdbc.update(lookup("validation/DeleteValidationEntityOperations"), singletonMap("ids", ids(validations)));
    }

    private void deleteTags(List<Validation> validations) {
        jdbc.update(lookup("validation/DeleteTags"), singletonMap("ids", ids(validations)));
    }

    private void createHistory(Validation validation) {

        MapSqlParameterSource params = new MapSqlParameterSource(prepareHistoricalParams(validation));

        GeneratedKeyHolder idHolder = new GeneratedKeyHolder();
        jdbc.update(lookup("validation/CreateValidationHistory"), params, idHolder, new String[]{"validation_version_id"});

        int validationVersionId = idHolder.getKey().intValue();

        createEntityOperationsHistory(validationVersionId, validation.getValidationEntities());
        createTagsHistory(validationVersionId, validation.getTags());
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

    private void createTagsHistory(int validationVersionId, Set<Tag> tags) {
        List<SqlParameterSource> params = new ArrayList<>();

        tags.forEach(tag -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("validationVersionId", validationVersionId);
            source.addValue("tagId", tag.getId());
            params.add(source);
        });

        jdbc.batchUpdate(lookup("validation/CreateValidationTagHistory"), params.toArray(new SqlParameterSource[]{}));
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
        deleteEntityOperations(singletonList(validation));
        deleteTags(singletonList(validation));

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

    private Map<String, Set<ValidationEntity>> loadValidationEntities(List<String> validationIds) {
        return jdbc.query(lookup("validation/LoadValidationEntities"), singletonMap("ids", validationIds), rse);
    }

    private Map<String, Set<Tag>> loadTags(List<String> validationIds) {
        return jdbc.query(lookup("validation/LoadTags"), singletonMap("ids", validationIds), tagsByValidationIdExtractor);
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
        Validation validation = jdbc.queryForObject(lookup("validation/LoadValidationVersion"),
                singletonMap("id", versionId), mapper);
        validation.setValidationEntities(loadEntitiesVersions(versionId));
        validation.setTags(loadTagsVersions(versionId));
        return validation;
    }

    private Set<ValidationEntity> loadEntitiesVersions(int validationVersionId) {
        Map<String, Set<ValidationEntity>> entitiesByValidationId = jdbc.query(lookup("validation/LoadValidationEntitiesVersions"),
                singletonMap("id", validationVersionId), rse);
        Set<ValidationEntity> result = new HashSet<>();
        for (Set<ValidationEntity> validationEntities : entitiesByValidationId.values()) {
            result.addAll(validationEntities);
        }
        return result;
    }

    private Set<Tag> loadTagsVersions(int validationVersionId) {
        List<Tag> data = jdbc.query(lookup("validation/LoadTagsVersion"), singletonMap("id", validationVersionId),
                TagDao.TAG_MAPPER);
        return new HashSet<>(data);
    }

    public List<ValidationExportRow> exportValidations() {
        return jdbc.query(lookup("validation/ExportValidations"), exportMapper);
    }

    @Override
    public String getCurrentCommentary(String validationId) {
        return jdbc.queryForObject(lookup("validation/GetCurrentCommentary"), singletonMap("validationId", validationId), String.class);
    }

    public List<String> loadValidationIdsByTagIds(List<String> tagIds) {
        if (tagIds.isEmpty()) {
            return Collections.emptyList();
        }
        return jdbc.queryForList(lookup("validation/LoadValidationIdsByTagIds"), singletonMap("ids", tagIds), String.class);
    }

}
