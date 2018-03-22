package project.model.message;

/** Сообщение пользователю об ошибке. Данные для экспорта. */
public class MessageExportRow {
    private String code;
    private String text;

    public MessageExportRow(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
