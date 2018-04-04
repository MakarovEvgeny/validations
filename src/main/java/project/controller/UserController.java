package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import project.model.user.RegisterUserDto;
import project.model.user.UserCard;
import project.service.UserService;

import javax.validation.Valid;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    @Qualifier("registerUserValidator")
    private Validator v;

    @InitBinder("registerUserDto")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(v);
    }

    @PostMapping("register")
    public void register(@RequestBody @Valid RegisterUserDto dto) {
        service.register(dto.getUsername(), dto.getPassword());
    }

    @RequestMapping("whoami")
    public UserCard whoAmI() {
        return service.whoAmI();
    }


}
