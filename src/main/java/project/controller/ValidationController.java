package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.model.query.SearchParams;
import project.model.validation.Validation;
import project.service.ModelService;

import java.util.List;

@RestController()
@RequestMapping("validation")
public class ValidationController {

    @Autowired
    private ModelService<Validation> service;

    @RequestMapping(value = "query", method = RequestMethod.POST)
    public List<Validation> find(@RequestBody SearchParams searchParams) {
        return service.find(searchParams);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Validation load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Validation operation) {
        service.create(operation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@RequestBody Validation operation) {
        service.update(operation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void remove(@RequestBody Validation validation) {
        service.remove(validation);
    }

}
