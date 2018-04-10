package project.dao.operation;

import project.model.CurrentCommentaryAware;

public interface OperationValidatorDao  extends CurrentCommentaryAware {

    boolean alreadyExists(String id);
    boolean nameAlreadyExists(String id, String name);
    boolean isUsed(String id);

}
