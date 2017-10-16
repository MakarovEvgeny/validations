package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.model.operation.Operation;
import project.service.ModelService;

import java.util.List;

@RestController
@RequestMapping("operation")
public class OperationController {

    @Autowired
    private ModelService<Operation> service;

    @RequestMapping(method = RequestMethod.GET)
    public List<Operation> find() {
        return service.find();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Operation load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Operation operation) {
        service.create(operation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@RequestBody Operation operation) {
        service.update(operation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void remove(@RequestBody Operation operation) {
        service.remove(operation);
    }

}
