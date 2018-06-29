package project.service;

import project.model.tag.MergeTag;
import project.model.validation.Validation;
import project.model.validation.ValidationDto;

public interface ValidationService extends VersionableModelService<Validation>, FindService<ValidationDto> {

    /** Объединяем несколько тегов в один */
    void mergeTags(MergeTag mergeTag);
}
