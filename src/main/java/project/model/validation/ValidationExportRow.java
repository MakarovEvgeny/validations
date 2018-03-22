package project.model.validation;

/**
 * Проверка aka Валидация. Данные экспорта.
 */
public class ValidationExportRow {
    private String code;
    private String severity;
    private String messageCode;
    private String entities ;
    private String operations;
    private String description;

    public ValidationExportRow(String code, String severity, String messageCode, String entities, String operations,
            String description) {
        this.code = code;
        this.severity = severity;
        this.messageCode = messageCode;
        this.entities = entities;
        this.operations = operations;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getSeverity() {
        return severity;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getEntities() {
        return entities;
    }

    public String getOperations() {
        return operations;
    }

    public String getDescription() {
        return description;
    }
}
