package project.model;

import java.util.Objects;

/**
 * Абстрактный класс, содержит базовые поля всех моделей.
 * @param <ID> тип id модели.
 */
public abstract class AbstractModel<ID> {

    /** Вводится пользователем, является семантическим обозначением сущности системы. */
    protected ID id;

    /** Наименование, человекопонятное. */
    protected String name;

    /** Описание - если не получается выразить смысл сущности в наименовании. */
    protected String description;

    public ID getId() {
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
        AbstractModel<?> that = (AbstractModel<?>) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

}
