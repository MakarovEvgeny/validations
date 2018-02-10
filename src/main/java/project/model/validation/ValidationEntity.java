package project.model.validation;

import project.model.entity.Entity;
import project.model.operation.Operation;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Сущность заданная для {@link Validation Валидации} с указанными операцями над этой сущностью.
 */
public class ValidationEntity {

    /** {@link Entity} Сущность. */
    private Entity entity;

    /**
     * Коллекция уникальных {@link project.model.operation.Operation операций} над сущностью.
     */
    private Set<Operation> operations = new HashSet<>();

    public ValidationEntity() {
    }

    public ValidationEntity(Entity entity, Set<Operation> operations) {
        this.entity = entity;
        this.operations = operations;
    }

    public Entity getEntity() {
        return entity;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationEntity that = (ValidationEntity) o;
        return Objects.equals(entity, that.entity) &&
                Objects.equals(operations, that.operations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, operations);
    }
}
