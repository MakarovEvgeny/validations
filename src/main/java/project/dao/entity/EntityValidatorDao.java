package project.dao.entity;

import project.model.CurrentCommentaryAware;

public interface EntityValidatorDao extends CurrentCommentaryAware {

    boolean alreadyExists(String id);
    boolean nameAlreadyExists(String id, String name);
    boolean isUsed(String id);

}
