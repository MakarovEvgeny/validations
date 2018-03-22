package project.dao.message;

import java.util.List;

import project.model.message.MessageExportRow;

public interface MessageExportDao {
    List<MessageExportRow> exportMessages();
}
