package project.service;

import project.model.validation.Validation;
import project.model.validation.ValidationDto;

public interface ValidationService extends VersionableModelService<Validation>, FindService<ValidationDto> {
}
