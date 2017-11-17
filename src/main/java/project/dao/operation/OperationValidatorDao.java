package project.dao.operation;

public interface OperationValidatorDao {

    boolean alreadyExists(String id);
    boolean nameAlreadyExists(String id, String name);
    boolean isUsed(String id);

}
