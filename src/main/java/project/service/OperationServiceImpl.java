package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dao.operation.OperationDao;
import project.model.Change;
import project.model.entity.Entity;
import project.model.operation.Operation;
import project.model.query.SearchParams;

import java.util.List;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {

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

    @Override
    public List<Change> getChanges(String operationId) {
        return dao.getChanges(operationId);
    }

    @Override
    public Operation loadVersion(int versionId) {
        return dao.loadVersion(versionId);
    }


}
