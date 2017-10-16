package project.dao.operation;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.dao.BaseVersionAwareModelDao;
import project.dao.ConcurrentModificationException;
import project.model.operation.Operation;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

@Repository
public class OperationDao extends BaseVersionAwareModelDao<Operation> {

    private RowMapper<Operation> mapper = (rs, rowNum) -> new Operation(rs.getString("entity_id"), rs.getString("name"), rs.getString("description"), rs.getInt("version"), rs.getString("commentary"));

    public Operation load(String operationId) {
        return jdbc.queryForObject("select * from operation where operation_id = :id", singletonMap("id", operationId), mapper);
    }

    public void create(Operation operation) {
        jdbc.update("insert into operation(operation_id, name, description, commentary) values (:id, :name, :description, :commentary)", prepareParams(operation));
        createHistory(operation);
    }

    private void createHistory(Operation operation) {
        jdbc.update("insert into operation_h(date, operation_id, name, description, version, commentary) values (:date, :id, :name, :description, :version, :commentary)", prepareHistoricalParams(operation));
    }

    private void createHistoryForRemoval(Operation operation) {
        jdbc.update("insert into operation_h(date, operation_id, version, commentary) values (:date, :id, :version, :commentary)", prepareHistoricalParams(operation));
    }

    public void update(Operation operation) {
        int rowsAffected = jdbc.update("update operation set name = :name, description = :description, version = version + 1, commentary = :commentary where operation_id = :id and version = :version", prepareParams(operation));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }
        createHistory(operation);
    }


    public void remove(Operation operation) {
        int rowsAffected = jdbc.update("delete from operation WHERE operation_id = :id and version = :version", prepareParams(operation));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }
        createHistoryForRemoval(operation);
    }

    public List<Operation> find() {
        return jdbc.query("select * from operation", mapper);
    }

    @Override
    protected Map<String, Object> prepareParams(Operation operation) {
        Map<String, Object> params = super.prepareParams(operation);
        params.put("name", operation.getName());
        params.put("description", operation.getDescription());
        return params;
    }

}
