package project.model;

/**
 * Интерфейс, который должны реализовывать все модели по которым ведется история.
 */
public interface Versionable {

    /** Получить версию медели. */
    int getVersion();

    /** Получить комментарий с причиной изменения. */
    String getCommentary();

}
