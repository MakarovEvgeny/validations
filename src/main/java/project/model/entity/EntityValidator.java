package project.model.entity;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EntityValidator implements Validator {

    private static final String NAME = "Сущность";

    @Override
    public boolean supports(Class<?> clazz) {
        return Entity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "id", "001", new String[] {NAME});
        ValidationUtils.rejectIfEmpty(errors, "name", "002", new String[] {NAME});
    }

}
