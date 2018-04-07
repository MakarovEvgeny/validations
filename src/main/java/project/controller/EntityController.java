package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import project.aspect.LoggingThat;
import project.model.Change;
import project.model.entity.Entity;
import project.model.eventlog.EventLogType;
import project.model.query.SearchParams;
import project.service.EntityService;

import javax.validation.Valid;
import java.util.List;

import static project.model.ClientOperation.QUERY;

@RequestMapping("entity")
@RestController
public class EntityController {

    private final EntityService service;

    private final Validator v;

    @Autowired
    public EntityController(EntityService service, @Qualifier("entityValidator") Validator v) {
        this.service = service;
        this.v = v;
    }

    @InitBinder("entity")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(v);
    }

    @RequestMapping(value = QUERY, method = RequestMethod.POST)
    @LoggingThat(type = EventLogType.SEARCH, operation = "Поиск сущности")
    public List<Entity> find(@RequestBody SearchParams searchParams) {
        return service.find(searchParams);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Entity load(@PathVariable String id){
        return service.load(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @LoggingThat(type = EventLogType.CREATE, operation = "Создание сущности")
    public void create(@RequestBody @Valid Entity entity) {
        service.create(entity);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @LoggingThat(type = EventLogType.UPDATE, operation = "Обновление данных сущности")
    public void update(@RequestBody @Valid Entity entity) {
        service.update(entity);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @LoggingThat(type = EventLogType.DELETE, operation = "Удаление сущности")
    public void remove(@RequestBody @Valid Entity entity) {
        service.remove(entity);
    }

    @RequestMapping(value = "{id}/change", method = RequestMethod.GET)
    public List<Change> getChanges(@PathVariable String id) {
        return service.getChanges(id);
    }

    @RequestMapping(value = "{id}/change/{versionId}", method = RequestMethod.GET)
    public Entity loadVersion(@PathVariable int versionId) {
        return service.loadVersion(versionId);
    }

}
