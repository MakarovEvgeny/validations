package project.model.validation;

import java.util.HashMap;
import java.util.Map;

/** Тип проверки. */
public enum Severity {

    ERROR(1, "Ошибка"),

    WARNING(2, "Предупреждение");

    private int id;

    private String name;

    static Map<Integer, Severity> map = new HashMap<>();

    static {
        map.put(ERROR.getId(), ERROR);
        map.put(WARNING.id, WARNING);
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
        Severity result = map.get(id);
        if (result == null) {
            throw new IllegalArgumentException("Bad severity id");
        }
        return result;
    }
}
