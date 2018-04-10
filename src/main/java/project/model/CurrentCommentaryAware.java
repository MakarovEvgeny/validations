package project.model;

public interface CurrentCommentaryAware {

    /** Получим комментарий из БД для актуальной версии. */
    String getCurrentCommentary(String modelId);

}
