package project.model.entity;

import project.model.BaseVersionAwareModel;

import java.util.Objects;

/**
 * Сущность системы.
 */
public class Entity extends BaseVersionAwareModel {

    /** Наименование, человекопонятное. */
    private String name;

    /** Описание - если не получается выразить смысл сущности в наименовании. */
    private String description;

    @SuppressWarnings("unused")
    public Entity() {
        //for spring
    }

    public Entity(String id, String name, String description, int version, String commentary) {
        super(id, version, commentary);
        this.name = name;
        this.description = description;
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
        if (!super.equals(o)) return false;
        Entity entity = (Entity) o;
        return Objects.equals(name, entity.name) &&
                Objects.equals(description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description);
    }

}
