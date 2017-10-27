package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.model.query.SearchParams;
import project.model.entity.Entity;
import project.service.ModelService;

import java.util.List;

@RequestMapping("entity")
@RestController
public class EntityController {

    @Autowired
    private ModelService<Entity> service;

    @RequestMapping(value = "query", method = RequestMethod.POST)
    public List<Entity> find(@RequestBody SearchParams searchParams) {
        return service.find(searchParams);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Entity load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Entity entity) {
        service.create(entity);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@RequestBody Entity entity) {
        service.update(entity);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void remove(@RequestBody Entity entity) {
        service.remove(entity);
    }

}
