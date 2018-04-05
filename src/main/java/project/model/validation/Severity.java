package project.model.validation;

import java.util.HashMap;
import java.util.Map;

/** Тип проверки. */
public enum Severity {

    ERROR(1, "Ошибка"),

    WARNING(2, "Предупреждение");

    private int id;

    private String name;

    static Map<Integer, Severity> cacheById = new HashMap<>();

    static {
        cacheById.put(ERROR.getId(), ERROR);
        cacheById.put(WARNING.getId(), WARNING);
    }

    Severity() {
        //for jackson
    }

    Severity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public static Severity resolveById(int id) {
        Severity result = cacheById.get(id);
        if (result == null) {
            throw new IllegalArgumentException("Bad severity id");
        }
        return result;
    }
}
