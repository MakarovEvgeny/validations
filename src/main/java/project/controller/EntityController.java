package project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EntityController {

    @RequestMapping("/")
    public String all() {
        return "nothing yet";
    }

}
