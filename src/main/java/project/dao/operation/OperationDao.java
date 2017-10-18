package project.dao.operation;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.dao.BaseVersionAwareModelDao;
import project.dao.ConcurrentModificationException;
import project.model.operation.Operation;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static project.dao.RequestRegistry.lookup;

@Repository
public class OperationDao extends BaseVersionAwareModelDao<Operation> {

    private RowMapper<Operation> mapper = (rs, rowNum) -> new Operation(rs.getString("id"), rs.getString("name"), rs.getString("description"), rs.getInt("version"), rs.getString("commentary"));

    public Operation load(String operationId) {
        return jdbc.queryForObject(lookup("operation/LoadOperation"), singletonMap("id", operationId), mapper);
    }

    public void create(Operation operation) {
        jdbc.update(lookup("operation/CreateOperation"), prepareParams(operation));
        createHistory(operation);
    }

    private void createHistory(Operation operation) {
        jdbc.update(lookup("operation/CreateOperationHistory"), prepareHistoricalParams(operation));
    }

    public void update(Operation operation) {
        int rowsAffected = jdbc.update(lookup("operation/UpdateOperation"), prepareParams(operation));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }
        createHistory(operation);
    }


    public void remove(Operation operation) {
        int rowsAffected = jdbc.update(lookup("operation/DeleteOperation"), prepareParams(operation));
        if (rowsAffected == 0) {
            throw new ConcurrentModificationException();
        }
        jdbc.update(lookup("operation/CreateOperationHistory"), prepareHistoricalParamsForRemove(operation));
    }

    public List<Operation> find() {
        return jdbc.query(lookup("operation/FindOperation"), mapper);
    }

    @Override
    protected Map<String, Object> prepareParams(Operation operation) {
        Map<String, Object> params = super.prepareParams(operation);
        params.put("name", operation.getName());
        params.put("description", operation.getDescription());
        return params;
    }

    private Map<String, Object> prepareHistoricalParamsForRemove(Operation model) {
        Map<String, Object> params = super.prepareHistoricalParams(model);
        params.put("name", null);
        params.put("description", null);
        return params;
    }

}
