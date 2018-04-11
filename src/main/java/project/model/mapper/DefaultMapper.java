package project.model.mapper;

import org.springframework.stereotype.Component;

/** Маппер, преобразует в json запросы и ответы. */
@Component
public class DefaultMapper extends AbstractMapper {

    @Override
    public String requestToJson(Object[] request) {
        return toJsonString(request);
    }

    @Override
    public String responseToJson(Object response) {
        return toJsonString(response);
    }

}
