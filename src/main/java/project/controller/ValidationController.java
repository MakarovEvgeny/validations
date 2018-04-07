package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import project.aspect.LoggingThat;
import project.model.Change;
import project.model.eventlog.EventLogType;
import project.model.query.SearchParams;
import project.model.validation.Validation;
import project.model.validation.ValidationDto;
import project.service.ValidationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("validation")
public class ValidationController {

    private final ValidationService service;

    private final Validator validator;

    @Autowired
    public ValidationController(ValidationService service, @Qualifier("validationValidator") Validator validator) {
        this.service = service;
        this.validator = validator;
    }

    @InitBinder("validation")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = "query", method = RequestMethod.POST)
    @LoggingThat(type = EventLogType.SEARCH, operation = "Поиск проверок")
    public List<ValidationDto> find(@RequestBody SearchParams searchParams) {
        return service.find(searchParams);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public Validation load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @LoggingThat(type = EventLogType.CREATE, operation = "Создание проверки")
    public void create(@RequestBody @Valid Validation operation) {
        service.create(operation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @LoggingThat(type = EventLogType.UPDATE, operation = "Обновление данных проверки")
    public void update(@RequestBody @Valid Validation operation) {
        service.update(operation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @LoggingThat(type = EventLogType.DELETE, operation = "Удаление проверки")
    public void remove(@RequestBody @Valid Validation validation) {
        service.remove(validation);
    }

    @RequestMapping(value = "{id}/change", method = RequestMethod.GET)
    public List<Change> getChanges(@PathVariable String id) {
        return service.getChanges(id);
    }

    @RequestMapping(value = "{id}/change/{versionId}", method = RequestMethod.GET)
    public Validation loadVersion(@PathVariable int versionId) {
        return service.loadVersion(versionId);
    }

}
