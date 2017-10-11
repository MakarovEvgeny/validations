package project.dao;

import project.model.BaseVersionAwareModel;

import java.util.List;

/** Основные методы dao. */
public interface AbstractDao<MODEL extends BaseVersionAwareModel> {

    MODEL load(String modelId);

    void create(MODEL model);

    void update(MODEL model);

    void remove(MODEL model);

    List<MODEL> find();

}
