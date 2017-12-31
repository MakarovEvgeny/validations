package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import project.model.Change;
import project.model.query.SearchParams;
import project.model.validation.Validation;
import project.model.validation.ValidationDto;
import project.service.ValidationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("validation")
public class ValidationController {

    @Autowired
    private ValidationService service;

    @Autowired
    @Qualifier("validationValidator")
    private Validator validator;

    @InitBinder("validation")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = "query", method = RequestMethod.POST)
    public List<ValidationDto> find(@RequestBody SearchParams searchParams) {
        return service.find(searchParams);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public Validation load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody @Valid Validation operation) {
        service.create(operation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@RequestBody @Valid Validation operation) {
        service.update(operation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
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
