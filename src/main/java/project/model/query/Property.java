package project.model.query;

/** Минимальный набор данных для конкретного поиска. */
public class Property {

    /** Наименование */
    private String property;
    /** Оператор */
    private Operator operator = Operator.EQUALS;
    /** Значение. */
    private String value;

    public Property() {
    }

    public Property(String property, Operator operator, String value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

}
