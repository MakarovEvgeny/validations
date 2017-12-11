package project.service;

import project.model.query.SearchParams;

import java.util.List;

public interface FindService<DTO> {

    List<DTO> find(SearchParams searchParams);

}
