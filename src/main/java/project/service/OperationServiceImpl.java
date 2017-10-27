package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dao.operation.OperationDao;
import project.model.operation.Operation;
import project.model.query.SearchParams;

import java.util.List;

@Service
@Transactional
public class OperationServiceImpl implements ModelService<Operation> {

    @Autowired
    private OperationDao dao;

    @Override
    public Operation load(String operationId) {
        return dao.load(operationId);
    }

    @Override
    public void create(Operation operation) {
        dao.create(operation);
    }

    @Override
    public void update(Operation operation) {
        dao.update(operation);
    }

    @Override
    public void remove(Operation operation) {
        dao.remove(operation);
    }

    @Override
    public List<Operation> find(SearchParams searchParams) {
        return dao.find(searchParams);
    }

}
