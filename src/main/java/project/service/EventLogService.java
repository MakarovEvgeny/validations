package project.service;

import project.model.eventlog.EventLog;

public interface EventLogService {
    void fire(EventLog eventLog);
}
