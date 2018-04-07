package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.dao.message.MessageDao;
import project.dao.validation.ValidationDao;
import project.model.message.MessageExportRow;
import project.model.validation.ValidationExportRow;

@Service
@Transactional
public class ExportServiceImpl implements ExportService {

    private final MessageDao messageDao;

    private final ValidationDao validationDao;

    @Autowired
    public ExportServiceImpl(MessageDao messageDao, ValidationDao validationDao) {
        this.messageDao = messageDao;
        this.validationDao = validationDao;
    }

    @Override
    public List<MessageExportRow> getMessages() {
        return messageDao.exportMessages();
    }

    @Override
    public List<ValidationExportRow> getValidations() {
        return validationDao.exportValidations();
    }
}
