package project.service;

import project.model.AbstractModel;

public interface ModelService<MODEL extends AbstractModel> {

    MODEL load(String modelId);

    void create(MODEL model);

    void update(MODEL model);

    void remove(MODEL model);

}
