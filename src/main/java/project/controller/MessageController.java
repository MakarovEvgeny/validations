package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.model.message.Message;
import project.service.ModelService;

import java.util.List;

@RequestMapping("message")
@RestController
public class MessageController {

    @Autowired
    private ModelService<Message> service;

    @RequestMapping(method = RequestMethod.GET)
    public List<Message> find() {
        return service.find();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Message load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Message message) {
        service.create(message);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@RequestBody Message message) {
        service.update(message);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void remove(@RequestBody Message message) {
        service.remove(message);
    }

}
