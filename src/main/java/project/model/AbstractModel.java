package project.model;

import java.util.Objects;

/**
 * Абстрактный класс, содержит базовые поля всех моделей.
 */
public abstract class AbstractModel implements Persistable {

    /** Вводится пользователем, является семантическим обозначением сущности системы. */
    protected String id;

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractModel that = (AbstractModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
