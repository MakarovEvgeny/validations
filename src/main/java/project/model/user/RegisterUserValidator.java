package project.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import project.dao.user.UserValidatorDao;

import java.util.Objects;

@Component
public class RegisterUserValidator implements Validator {

    private final UserValidatorDao dao;

    @Autowired
    public RegisterUserValidator(UserValidatorDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterUserDto.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterUserDto dto = (RegisterUserDto) target;
        ValidationUtils.rejectIfEmpty(errors, "username", "016", new String[]{"Имя пользователя"});
        ValidationUtils.rejectIfEmpty(errors, "password", "016", new String[]{"Пароль"});
        ValidationUtils.rejectIfEmpty(errors, "password2", "016", new String[]{"Повторно введенный пароль"});

        if (!Objects.equals(dto.getPassword(), dto.getPassword2())) {
            errors.rejectValue("password", "017");
        }

        if (dao.usernameAlreadyExists(dto.getUsername())) {
                errors.rejectValue("username", "018");
        }
    }

}
