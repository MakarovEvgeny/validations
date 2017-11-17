package project.dao.entity;

public interface EntityValidatorDao {

    boolean alreadyExists(String id);
    boolean nameAlreadyExists(String id, String name);
    boolean isUsed(String id);

}
