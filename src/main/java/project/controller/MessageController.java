package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import project.aspect.LoggingThat;
import project.model.Change;
import project.model.eventlog.EventLogType;
import project.model.message.Message;
import project.model.query.SearchParams;
import project.service.MessageService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("message")
@RestController
public class MessageController {

    private final MessageService service;

    private final Validator validator;

    @Autowired
    public MessageController(MessageService service, @Qualifier("messageValidator") Validator validator) {
        this.service = service;
        this.validator = validator;
    }

    @InitBinder("message")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = "query", method = RequestMethod.POST)
    @LoggingThat(type = EventLogType.SEARCH, operation = "Поиск сообщения")
    public List<Message> find(@RequestBody SearchParams searchParams) {
        return service.find(searchParams);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Message load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @LoggingThat(type = EventLogType.CREATE, operation = "Создание сообщения")
    public void create(@RequestBody @Valid Message message) {
        service.create(message);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @LoggingThat(type = EventLogType.UPDATE, operation = "Обновление данных сообщения")
    public void update(@RequestBody @Valid Message message) {
        service.update(message);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @LoggingThat(type = EventLogType.DELETE, operation = "Удаление сообщения")
    public void remove(@RequestBody @Valid Message message) {
        service.remove(message);
    }

    @RequestMapping(value = "{id}/change", method = RequestMethod.GET)
    public List<Change> getChanges(@PathVariable String id) {
        return service.getChanges(id);
    }

    @RequestMapping(value = "{id}/change/{versionId}", method = RequestMethod.GET)
    public Message loadVersion(@PathVariable int versionId) {
        return service.loadVersion(versionId);
    }
}
