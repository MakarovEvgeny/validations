package project.model.eventlog;


/** Лог произвольного события действия пользователя. */
public class EventLog {
    private EventLogType type;
    private String operation;
    private Object request;
    private Object response;
    private String commentary;

    public EventLogType getType() {
        return type;
    }

    public String getOperation() {
        return operation;
    }

    public Object getRequest() {
        return request;
    }

    public Object getResponse() {
        return response;
    }

    public String getCommentary() {
        return commentary;
    }

    public EventLog request(Object request) {
        this.request = request;
        return this;
    }

    public EventLog response(Object response) {
        this.response = response;
        return this;
    }

    public EventLog operation(String operation) {
        this.operation = operation;
        return this;
    }

    public EventLog type(EventLogType type) {
        this.type = type;
        return this;
    }

    public EventLog commentary(String commentary) {
        this.commentary = commentary;
        return this;
    }
}
