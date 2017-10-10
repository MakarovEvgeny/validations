package project.model;

/**
 * Интерфейс, который должны реалзовывать все модели по которым ведется история.
 */
public interface VersionAware {

    /** Получить версию медели. */
    int getVersion();

    /** Получить комментарий с причиной изменения. */
    String getCommentary();

}
