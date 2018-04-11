package project.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;

import project.model.eventlog.EventLog;
import project.model.mapper.Mapper;
import project.service.EventLogService;

@Aspect
@Component
public class EventLogAspect {

    private final EventLogService logService;

    private final ApplicationContext context;

    @Autowired
    public EventLogAspect(EventLogService logService, ApplicationContext context) {
        this.logService = logService;
        this.context = context;
    }

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

        Mapper mapper = lookupMapper(logInfo.mapper());

        EventLog eventLog = new EventLog()
                .type(logInfo.type())
                .operation(logInfo.operation())
                .commentary(logInfo.commentary())
                .request(mapper.requestToJson(pjp.getArgs()))
                .response(mapper.responseToJson(result));
        logService.fire(eventLog);
        if (resultException != null) {
            throw resultException;
        }
        return result;
    }

    private Mapper lookupMapper(Class<? extends Mapper> clazz) {
        return context.getBean(clazz);
    }

}
