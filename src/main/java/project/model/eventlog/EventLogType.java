package project.model.eventlog;

import java.util.HashMap;
import java.util.Map;

/** Тип журнала. */
public enum EventLogType {

    SEARCH(1, "Поиск"),

    CREATE(2, "Создание"),
    UPDATE(3, "Обновление"),
    DELETE(4, "Удаление"),

    EXPORT(5, "Отчет")

    ;

    private int id;

    private String name;

    static Map<Integer, EventLogType> cacheById = new HashMap<>();

    static {
        cacheById.put(SEARCH.getId(), SEARCH);
        cacheById.put(CREATE.getId(), CREATE);
        cacheById.put(UPDATE.getId(), UPDATE);
        cacheById.put(DELETE.getId(), DELETE);
        cacheById.put(EXPORT.getId(), EXPORT);
    }

    EventLogType() {
        //for jackson
    }

    EventLogType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public static EventLogType resolveById(int id) {
        EventLogType result = cacheById.get(id);
        if (result == null) {
            throw new IllegalArgumentException("Bad eventlogtype id");
        }
        return result;
    }
}
