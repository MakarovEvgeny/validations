package project.model.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import project.dao.operation.OperationValidatorDao;
import project.model.ClientOperation;

import static org.springframework.util.StringUtils.isEmpty;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class OperationValidator implements Validator {

    private static final String NAME = "Операция";

    private final ServletWebRequest request;

    private final OperationValidatorDao dao;

    @Autowired
    public OperationValidator(ServletWebRequest request, OperationValidatorDao dao) {
        this.request = request;
        this.dao = dao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Operation.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Operation operation = (Operation) target;
        ClientOperation op = ClientOperation.getClientOperation(request);

        ValidationUtils.rejectIfEmpty(errors, "id", "001", new String[]{NAME});
        ValidationUtils.rejectIfEmpty(errors, "name", "002", new String[]{NAME});
        ValidationUtils.rejectIfEmpty(errors, "commentary", "005", new String[]{NAME});

        String id = operation.getId();
        String name = operation.getName();

        if (op == ClientOperation.CREATE) {
            if (!isEmpty(id) && dao.alreadyExists(id)) {
                errors.rejectValue("id", "003", new String[]{NAME}, null);
            }
            if (!isEmpty(name) && dao.nameAlreadyExists(null, name)) {
                errors.rejectValue("id", "004", new String[]{NAME}, null);
            }
        }

        if (op == ClientOperation.UPDATE) {
            if (!isEmpty(id) && !isEmpty(name) && dao.nameAlreadyExists(id, name)) {
                errors.rejectValue("id", "004", new String[]{NAME}, null);
            }
        }

        if (op == ClientOperation.DELETE) {
            if (!isEmpty(id) && dao.isUsed(id)) {
                errors.rejectValue("id", "006", new String[]{NAME}, null);
            }
        }
    }

}
