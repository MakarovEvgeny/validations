package project.dao;

import project.model.AbstractModel;

import java.util.List;

/** Основные методы dao. */
public interface AbstractDao<MODEL extends AbstractModel> {

    MODEL load(String modelId);

    void create(MODEL model);

    void update(MODEL model);

    void remove(MODEL model);

    List<MODEL> find();

}
