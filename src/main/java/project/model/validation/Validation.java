package project.model.validation;

import project.model.BaseVersionAwareModel;
import project.model.entity.Entity;
import project.model.message.Message;
import project.model.operation.Operation;

import java.util.Objects;
import java.util.Set;

/**
 * Проверка aka Валидация.
 */
public class Validation extends BaseVersionAwareModel {

    /** id {@link project.model.message.Message} */
    private Message message;

    /** Описание проверки, бизнес требования. */
    private String description;

    /** Коллекция уникальных id {@link project.model.entity.Entity} */
    private Set<Entity> entities;

    /** Коллекция уникальных id {@link project.model.operation.Operation} */
    private Set<Operation> operations;

    public Validation() {
        //for spring
    }

    public Validation(String id, Message message, String description, int version, String commentary) {
        super(id, version, commentary);
        this.message = message;
        this.description = description;
    }

    public Message getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public void setEntities(Set<Entity> entities) {
        this.entities = entities;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Validation that = (Validation) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(description, that.description) &&
                Objects.equals(entities, that.entities) &&
                Objects.equals(operations, that.operations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), message, description, entities, operations);
    }
}
