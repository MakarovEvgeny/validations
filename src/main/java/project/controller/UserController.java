package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import project.aspect.LoggingThat;
import project.model.eventlog.EventLogType;
import project.model.user.RegisterUserDto;
import project.model.user.UserCard;
import project.service.UserService;

import javax.validation.Valid;

@RequestMapping("user")
@RestController
public class UserController {

    private final UserService service;

    private final Validator v;

    @Autowired
    public UserController(UserService service, @Qualifier("registerUserValidator") Validator v) {
        this.service = service;
        this.v = v;
    }

    @InitBinder("registerUserDto")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(v);
    }

    @PostMapping("register")
    @LoggingThat(type = EventLogType.CREATE, operation = "Создание пользователя")
    public void register(@RequestBody @Valid RegisterUserDto dto) {
        service.register(dto.getUsername(), dto.getPassword());
    }

    @RequestMapping("whoami")
    public UserCard whoAmI() {
        return service.whoAmI();
    }


}
