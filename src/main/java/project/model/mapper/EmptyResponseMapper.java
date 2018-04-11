package project.model.mapper;

import org.springframework.stereotype.Component;

/** Маппер, преобразует в json только запросы, содержимое ответов игнорируется. */
@Component
public class EmptyResponseMapper extends AbstractMapper {

    @Override
    public String requestToJson(Object[] request) {
        return toJsonString(request);
    }

    @Override
    public String responseToJson(Object response) {
        return toJsonString(null);
    }

}
