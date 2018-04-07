package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import project.dao.eventlog.EventLogDao;
import project.model.eventlog.EventLog;

@Transactional
@Service
public class EventLogServiceImpl implements EventLogService {

    private final EventLogDao eventLogDao;

    @Autowired
    public EventLogServiceImpl(EventLogDao eventLogDao) {
        this.eventLogDao = eventLogDao;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void fire(EventLog eventLog) {
        eventLogDao.store(eventLog);
    }
}
