package project.dao;

import project.model.AbstractModel;

/** Основные методы dao. */
public interface AbstractDao<MODEL extends AbstractModel> {

    MODEL load(String modelId);

    void create(MODEL model);

    void update(MODEL model);

    void remove(MODEL model);

}
