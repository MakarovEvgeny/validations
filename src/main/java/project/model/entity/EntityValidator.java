package project.model.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import project.dao.entity.EntityValidatorDao;
import project.model.ClientOperation;

import static org.springframework.util.StringUtils.isEmpty;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class EntityValidator implements Validator {

    private static final String NAME = "Сущность";

    private final ServletWebRequest request;

    private final EntityValidatorDao dao;

    @Autowired
    public EntityValidator(ServletWebRequest request, EntityValidatorDao dao) {
        this.request = request;
        this.dao = dao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Entity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Entity entity = (Entity) target;
        ClientOperation operation = ClientOperation.getClientOperation(request);

        ValidationUtils.rejectIfEmpty(errors, "id", "001", new String[]{NAME});
        ValidationUtils.rejectIfEmpty(errors, "name", "002", new String[]{NAME});
        ValidationUtils.rejectIfEmpty(errors, "commentary", "005", new String[]{NAME});

        String id = entity.getId();
        String name = entity.getName();

        if (operation == ClientOperation.CREATE) {
            if (!isEmpty(id) && dao.alreadyExists(id)) {
                errors.rejectValue("id", "003", new String[]{NAME}, null);
            }
            if (!isEmpty(name) && dao.nameAlreadyExists(null, name)) {
                errors.rejectValue("id", "004", new String[]{NAME}, null);
            }
        }

        if (operation == ClientOperation.UPDATE) {
            if (!isEmpty(id) && !isEmpty(name) && dao.nameAlreadyExists(id, name)) {
                errors.rejectValue("id", "004", new String[]{NAME}, null);
            }
        }

        if (operation == ClientOperation.DELETE) {
            if (!isEmpty(id) && dao.isUsed(id)) {
                errors.rejectValue("id", "006", new String[]{NAME}, null);
            }
        }
    }

}
