package project.service;

import java.util.List;

import project.model.message.MessageExportRow;
import project.model.validation.ValidationExportRow;

public interface ExportService {

    List<MessageExportRow> getMessages();

    List<ValidationExportRow> getValidations();
}
