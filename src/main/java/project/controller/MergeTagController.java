package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.aspect.LoggingThat;
import project.model.eventlog.EventLogType;
import project.model.tag.MergeTag;
import project.service.ValidationService;

@RequestMapping("mergeTag")
@RestController
public class MergeTagController {

    private final ValidationService validationService;

    @Autowired
    public MergeTagController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @LoggingThat(type = EventLogType.CREATE, operation = "Объединяем теги")
    public void create(@RequestBody MergeTag mergeTag) {
        validationService.mergeTags(mergeTag);
    }
}
