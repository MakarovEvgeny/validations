package project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.aspect.LoggingThat;
import project.model.Change;
import project.model.eventlog.EventLogType;
import project.model.query.SearchParams;
import project.model.tag.Tag;
import project.service.TagService;

@RequestMapping("tag")
@RestController
public class TagController {

    private final TagService service;

    @Autowired
    public TagController(TagService tagService) {
        this.service = tagService;
    }

    @RequestMapping(value = "query", method = RequestMethod.POST)
    @LoggingThat(type = EventLogType.SEARCH, operation = "Поиск тега")
    public List<Tag> find(@RequestBody SearchParams searchParams) {
        return service.find(searchParams);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Tag load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @LoggingThat(type = EventLogType.CREATE, operation = "Создание тега")
    public void create(@RequestBody @Valid Tag tag) {
        service.create(tag);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @LoggingThat(type = EventLogType.UPDATE, operation = "Обновление данных тега")
    public void update(@RequestBody @Valid Tag tag) {
        service.update(tag);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @LoggingThat(type = EventLogType.DELETE, operation = "Удаление тега")
    public void remove(@RequestBody @Valid Tag tag) {
        service.remove(tag);
    }

    @RequestMapping(value = "{id}/change", method = RequestMethod.GET)
    public List<Change> getChanges(@PathVariable String id) {
        return service.getChanges(id);
    }

    @RequestMapping(value = "{id}/change/{versionId}", method = RequestMethod.GET)
    public Tag loadVersion(@PathVariable int versionId) {
        return service.loadVersion(versionId);
    }
}
