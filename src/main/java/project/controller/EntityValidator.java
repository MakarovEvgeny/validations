package project.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.model.entity.Entity;

@Component
public class EntityValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Entity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

}
