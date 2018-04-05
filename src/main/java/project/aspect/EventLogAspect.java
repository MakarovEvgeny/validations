package project.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;

import project.model.eventlog.EventLog;
import project.service.EventLogService;

@Aspect
@Component
public class EventLogAspect {

    @Autowired
    private EventLogService logService;

    @Pointcut(value="execution(public * project..*(..))")
    public void anyPublicMethod() {}


    @Around("anyPublicMethod() && @annotation(logInfo)")
    public Object logEvent(ProceedingJoinPoint pjp, LoggingThat logInfo) throws Throwable {
        Object result;
        Throwable resultException = null;
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            result = throwable;
            resultException = throwable;
        }

        EventLog eventLog = new EventLog()
                .type(logInfo.type())
                .operation(logInfo.operation())
                .commentary(logInfo.commentary())
                .request(pjp.getArgs());
        if (logInfo.logResult()) {
            eventLog = eventLog.response(result);
        }
        logService.fire(eventLog);
        if (resultException != null) {
            throw resultException;
        }
        return result;
    }

}
