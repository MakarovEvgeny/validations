package project.model.mapper;

import org.springframework.stereotype.Component;
import project.model.user.RegisterUserDto;

/** Маппер для карточки регистрации пользователя, пароль пользователя не попадает в лог. */
@Component
public class RegisterUserDtoMapper extends EmptyResponseMapper {

    @Override
    public String requestToJson(Object[] request) {
        RegisterUserDto dto = (RegisterUserDto) request[0];

        RegisterUserDto copy = new RegisterUserDto(dto.getUsername(), "*", "*");
        return toJsonString(copy);
    }

}
