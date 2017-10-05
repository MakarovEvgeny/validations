package project.model;

import java.util.Objects;

/**
 * Сущность системы.
 */
public class Entity {

    /** Вводится пользователем, является семантическим обозначением сущности системы. */
    private String id;

    /** Наименование, человекопонятное. */
    private String name;

    /** Описание - если не получается вырязить смысл сущности в наименовании. */
    private String description;

    @SuppressWarnings("unused")
    public Entity() {
        //for spring
    }

    public Entity(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(name, entity.name) &&
                Objects.equals(description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

}
