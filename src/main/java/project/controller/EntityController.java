package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.model.entity.Entity;
import project.service.ModelService;

import java.util.List;

@RestController
public class EntityController {

    @Autowired
    private ModelService<Entity> service;

    @RequestMapping(value = "/entity", method = RequestMethod.GET)
    public List<Entity> find() {
        return service.find();
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.GET)
    public Entity load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    public void create(@RequestBody Entity entity) {
        service.create(entity);
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.PUT)
    public void update(@RequestBody Entity entity) {
        service.update(entity);
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.DELETE)
    public void remove(@RequestBody Entity entity) {
        service.remove(entity);
    }

}
