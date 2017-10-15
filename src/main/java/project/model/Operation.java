package project.model;

import project.model.entity.Entity;

import java.util.Objects;

/**
 * Операция над {@link Entity Сущностью}.
 */
public class Operation extends BaseVersionAwareModel {

    /** Наименование, человекопонятное. */
    private String name;

    /** Описание - если не получается выразить смысл сущности в наименовании. */
    private String description;

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
        Operation operation = (Operation) o;
        return Objects.equals(name, operation.name) &&
                Objects.equals(description, operation.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description);
    }

}
