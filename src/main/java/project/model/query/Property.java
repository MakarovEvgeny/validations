package project.model.query;

/** Минимальный набор данных для конкретного поиска. */
public class Property {

    /** Наименование */
    private String property;
    /** Оператор */
    private String operator = "=";
    /** Значение. */
    private String value;

    public Property() {
    }

    public Property(String property, String operator, String value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

}
