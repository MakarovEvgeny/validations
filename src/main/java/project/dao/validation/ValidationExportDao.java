package project.dao.validation;

import java.util.List;

import project.model.validation.ValidationExportRow;

public interface ValidationExportDao {
    List<ValidationExportRow> exportValidations();
}
