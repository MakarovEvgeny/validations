package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.model.ClientValidationFailureData;
import project.model.CodeAndMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Обработчик ошибок проверки клиентских данных. */
@ControllerAdvice
public class ClientValidationHandler {

    private static final Locale RU_LOCALE = new Locale("ru");

    private final MessageSource messageSource;

    @Autowired
    public ClientValidationHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ClientValidationFailureData> handleValidationFailure(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<CodeAndMessage> result = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(e -> result.add(new CodeAndMessage(e.getCode(), messageSource.getMessage(e, RU_LOCALE))));

        return new ResponseEntity<>(new ClientValidationFailureData(result), HttpStatus.BAD_REQUEST);
    }

}
