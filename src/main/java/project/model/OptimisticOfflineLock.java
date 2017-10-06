package project.model;

/** Интерфейс, обозначающий отслеживание конкурентного изменения объекта в БД. */
public interface OptimisticOfflineLock {

    int getVersion();

}
