package project.model;

import java.util.Objects;

/**
 * Сущность системы.
 */
public class Entity implements OptimisticOfflineLock {

    /** Вводится пользователем, является семантическим обозначением сущности системы. */
    private String id;

    /** Наименование, человекопонятное. */
    private String name;

    /** Описание - если не получается вырязить смысл сущности в наименовании. */
    private String description;

    /** Версия сущности. */
    private int version = 1;

    /** Комментарий. */
    private String commentary;

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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getVersion() {
        return version;
    }

    public String getCommentary() {
        return commentary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return version == entity.version &&
                Objects.equals(id, entity.id) &&
                Objects.equals(name, entity.name) &&
                Objects.equals(description, entity.description) &&
                Objects.equals(commentary, entity.commentary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, version, commentary);
    }

}
