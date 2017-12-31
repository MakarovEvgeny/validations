package project.service;

import project.model.BaseVersionableModel;

public interface VersionableModelService<MODEL extends BaseVersionableModel> extends ModelService<MODEL>, ChangesAware {

    MODEL loadVersion(int versionId);

}
