package project.model.tag;

import project.model.*;

import java.util.Objects;

/** Тег для проверок. */
public class Tag extends BaseVersionableModel {

    /** Название тега. */
    private String name;

    /** Описание тега. */
    private String description;

    @SuppressWarnings("unused")
    public Tag() {
        //for spring
    }

    public Tag(String id, String name, int version, String commentary, String description) {
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
        Tag message = (Tag) o;
        return Objects.equals(name, message.name) &&
                Objects.equals(description, message.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description);
    }
}
