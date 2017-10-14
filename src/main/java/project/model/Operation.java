package project.model;

import project.model.entity.Entity;

/**
 * Операция над {@link Entity Сущностью}.
 */
public class Operation extends BaseVersionAwareModel {

    public Operation() {
        //for spring
    }

    public Operation(String id, String name, String description, int version, String commentary) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.version = version;
        this.commentary = commentary;
    }

}
