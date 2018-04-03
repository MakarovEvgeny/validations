package project.dao.eventlog;

import project.model.eventlog.EventLog;

public interface EventLogDao {

    void store(EventLog eventLog);
}
