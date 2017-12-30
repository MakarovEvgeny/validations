package project.model;

import java.util.Objects;

/**
 * Базовый класс моделей, по которым ведется история.
 */
public class BaseVersionableModel extends AbstractModel implements Versionable, OptimisticOfflineLock {

    /** Версия сущности. */
    protected int version;

    /** Комментарий. */
    protected String commentary;

    public BaseVersionableModel() {
    }

    public BaseVersionableModel(String id, int version, String commentary) {
        super(id);
        this.version = version;
        this.commentary = commentary;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public String getCommentary() {
        return commentary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseVersionableModel that = (BaseVersionableModel) o;
        return version == that.version &&
                Objects.equals(commentary, that.commentary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), version, commentary);
    }

}
