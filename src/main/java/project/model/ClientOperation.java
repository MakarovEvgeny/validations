package project.model;

import org.springframework.web.context.request.ServletWebRequest;

public enum ClientOperation {

    LOAD,
    CREATE,
    UPDATE,
    DELETE,
    FIND;

    public static final String QUERY = "query";

    public static ClientOperation getClientOperation(ServletWebRequest request) {

        switch (request.getHttpMethod()) {
            case DELETE:
                return DELETE;
            case PUT:
                return UPDATE;
            case GET:
                return LOAD;
            case POST: {
                String uri = request.getRequest().getRequestURI();
                return uri.contains(QUERY) ? FIND : CREATE;
            }
            default:
                throw new IllegalArgumentException("Bad http method to define client operation");
        }

    }

}
