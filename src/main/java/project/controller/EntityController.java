package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import project.model.entity.Entity;
import project.model.query.SearchParams;
import project.service.EntityService;

import javax.validation.Valid;
import java.util.List;

import static project.model.ClientOperation.QUERY;

@RequestMapping("entity")
@RestController
public class EntityController {

    @Autowired
    private EntityService service;

    @Autowired
    @Qualifier("entityValidator")
    private Validator v;

    @InitBinder("entity")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(v);
    }

    @RequestMapping(value = QUERY, method = RequestMethod.POST)
    public List<Entity> find(@RequestBody SearchParams searchParams) {
        return service.find(searchParams);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Entity load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody @Valid Entity entity) {
        service.create(entity);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@RequestBody @Valid Entity entity) {
        service.update(entity);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void remove(@RequestBody @Valid Entity entity) {
        service.remove(entity);
    }

}
