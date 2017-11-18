package project.dao.message;

public interface MessageValidatorDao {

    boolean alreadyExists(String id);
    boolean sameTextAlreadyExists(String id, String name);
    boolean isUsed(String id);

}
