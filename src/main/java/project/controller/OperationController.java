package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import project.aspect.LoggingThat;
import project.model.Change;
import project.model.eventlog.EventLogType;
import project.model.operation.Operation;
import project.model.query.SearchParams;
import project.service.OperationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("operation")
public class OperationController {

    private final OperationService service;

    private final Validator validator;

    @Autowired
    public OperationController(OperationService service, @Qualifier("operationValidator") Validator validator) {
        this.service = service;
        this.validator = validator;
    }

    @InitBinder("operation")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = "query", method = RequestMethod.POST)
    @LoggingThat(type = EventLogType.SEARCH, operation = "Поиск операции")
    public List<Operation> find(@RequestBody SearchParams searchParams) {
        return service.find(searchParams);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Operation load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @LoggingThat(type = EventLogType.CREATE, operation = "Создание операции")
    public void create(@RequestBody @Valid Operation operation) {
        service.create(operation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @LoggingThat(type = EventLogType.UPDATE, operation = "Обновление данных операции")
    public void update(@RequestBody @Valid Operation operation) {
        service.update(operation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @LoggingThat(type = EventLogType.DELETE, operation = "Удаление операции")
    public void remove(@RequestBody @Valid Operation operation) {
        service.remove(operation);
    }

    @RequestMapping(value = "{id}/change", method = RequestMethod.GET)
    public List<Change> getChanges(@PathVariable String id) {
        return service.getChanges(id);
    }

    @RequestMapping(value = "{id}/change/{versionId}", method = RequestMethod.GET)
    public Operation loadVersion(@PathVariable int versionId) {
        return service.loadVersion(versionId);
    }

}
