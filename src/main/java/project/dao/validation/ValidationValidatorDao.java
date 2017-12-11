package project.dao.validation;

public interface ValidationValidatorDao {

    boolean alreadyExists(String id);
    boolean messageExists(String messageId);

}
