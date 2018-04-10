package project.dao.validation;

import project.model.CurrentCommentaryAware;

public interface ValidationValidatorDao extends CurrentCommentaryAware {

    boolean alreadyExists(String id);
    boolean messageExists(String messageId);

}
