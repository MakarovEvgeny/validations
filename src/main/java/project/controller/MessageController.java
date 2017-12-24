package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import project.model.Change;
import project.model.message.Message;
import project.model.query.SearchParams;
import project.service.MessageService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("message")
@RestController
public class MessageController {

    @Autowired
    private MessageService service;

    @Autowired
    @Qualifier("messageValidator")
    private Validator validator;

    @InitBinder("message")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = "query", method = RequestMethod.POST)
    public List<Message> find(@RequestBody SearchParams searchParams) {
        return service.find(searchParams);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Message load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody @Valid Message message) {
        service.create(message);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@RequestBody @Valid Message message) {
        service.update(message);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void remove(@RequestBody @Valid Message message) {
        service.remove(message);
    }

    @RequestMapping(value = "{id}/changes", method = RequestMethod.GET)
    public List<Change> getChanges(@PathVariable String id) {
        return service.getChanges(id);
    }

}
