package project.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.Nullable;

abstract class AbstractMapper implements Mapper {

    private ObjectMapper mapper = new ObjectMapper();

    String toJsonString(@Nullable Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
