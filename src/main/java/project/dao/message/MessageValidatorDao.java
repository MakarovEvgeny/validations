package project.dao.message;

import project.model.CurrentCommentaryAware;

public interface MessageValidatorDao extends CurrentCommentaryAware {

    boolean alreadyExists(String id);
    boolean sameTextAlreadyExists(String id, String name);
    boolean isUsed(String id);

}
