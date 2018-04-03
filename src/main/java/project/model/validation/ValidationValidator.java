package project.model.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import project.dao.validation.ValidationValidatorDao;
import project.model.ClientOperation;
import project.model.message.Message;

import static org.springframework.util.StringUtils.isEmpty;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class ValidationValidator implements Validator {

    private static final String NAME = "Проверка";

    @Autowired
    private ServletWebRequest request;

    @Autowired
    private ValidationValidatorDao dao;

    @Override
    public boolean supports(Class<?> clazz) {
        return Validation.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Validation validation = (Validation) target;
        ClientOperation op = ClientOperation.getClientOperation(request);

        ValidationUtils.rejectIfEmpty(errors, "id", "001", new String[]{NAME});

        String id = validation.getId();

        if (op == ClientOperation.CREATE) {
            if (!isEmpty(id) && dao.alreadyExists(id)) {
                errors.rejectValue("id", "003", new String[]{NAME}, null);
            }
        }

        if (op == ClientOperation.CREATE || op == ClientOperation.UPDATE) {
            if (validation.getSeverity() == null) {
                errors.rejectValue("severity", "011", new String[]{NAME}, null);
            }

            if (validation.getValidationEntities().isEmpty()) {
                errors.rejectValue("validationEntities", "009", new String[]{NAME}, null);
            }

            ValidationEntityValidator vev = new ValidationEntityValidator();
            for (ValidationEntity validationEntity : validation.getValidationEntities()) {
                if (validationEntity.getOperations().isEmpty()) {
                    BeanPropertyBindingResult veErrors = new BeanPropertyBindingResult(validationEntity, "validation");
                    ValidationUtils.invokeValidator(vev, validationEntity, veErrors);
                    errors.addAllErrors(veErrors);
                }
            }

            Message message = validation.getMessage();
            if (message == null || isEmpty(message.getId())) {
                errors.rejectValue("message", "012", new String[]{NAME}, null);
            } else if (!dao.messageExists(message.getId())) {
                errors.rejectValue("message", "013", new String[]{NAME}, null);
            }

            if (isEmpty(validation.getDescription())) {
                errors.rejectValue("description", "014", new String[]{NAME}, null);
            }

            if (isEmpty(validation.getCommentary())) {
                errors.rejectValue("commentary", "015", new String[]{NAME}, null);
            }

        }

        if (op == ClientOperation.DELETE) {
            ValidationUtils.rejectIfEmpty(errors, "commentary", "005", new String[]{NAME});
        }
    }

    private class ValidationEntityValidator implements Validator {

        @Override
        public boolean supports(Class<?> clazz) {
            return ValidationEntity.class == clazz;
        }

        @Override
        public void validate(Object target, Errors errors) {
            errors.rejectValue("operations", "010", new String[]{NAME}, null);
        }

    }
}
