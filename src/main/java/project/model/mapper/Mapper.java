package project.model.mapper;

/** Интерфейс для преобразования запросов/ответов в json при логировании пользовательских операций. */
public interface Mapper {

    String requestToJson(Object[] request);

    String responseToJson(Object response);

}
