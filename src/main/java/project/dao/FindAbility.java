package project.dao;

import project.model.query.SearchParams;

import java.util.List;

/**
 * Объект реализующий этот интерфейс может осуществлять поиск по критериям.
 * @param <DTO> то, что будет найдено.
 */
public interface FindAbility<DTO> {

    List<DTO> find(SearchParams searchParams);

}
