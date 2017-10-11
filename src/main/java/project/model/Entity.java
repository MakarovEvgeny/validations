package project.model;

/**
 * Сущность системы.
 */
public class Entity extends BaseVersionAwareModel {

    @SuppressWarnings("unused")
    public Entity() {
        //for spring
    }

    public Entity(String id, String name, String description, int version, String commentary) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.version = version;
        this.commentary = commentary;
    }

}
