package project.model.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import project.dao.message.MessageValidatorDao;
import project.model.ClientOperation;

import static org.springframework.util.StringUtils.isEmpty;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class MessageValidator implements Validator {

    private static final String NAME = "Сообщение";

    private final ServletWebRequest request;

    private final MessageValidatorDao dao;

    @Autowired
    public MessageValidator(ServletWebRequest request, MessageValidatorDao dao) {
        this.request = request;
        this.dao = dao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Message.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Message message = (Message) target;
        ClientOperation operation = ClientOperation.getClientOperation(request);

        ValidationUtils.rejectIfEmpty(errors, "id", "001", new String[]{NAME});
        ValidationUtils.rejectIfEmpty(errors, "text", "007", new String[]{NAME});
        ValidationUtils.rejectIfEmpty(errors, "commentary", "005", new String[]{NAME});

        String id = message.getId();
        String text = message.getText();

        if (operation == ClientOperation.CREATE) {
            if (!isEmpty(id) && dao.alreadyExists(id)) {
                errors.rejectValue("id", "003", new String[]{NAME}, null);
            }
            if (!isEmpty(text) && dao.sameTextAlreadyExists(null, text)) {
                errors.rejectValue("id", "008", new String[]{NAME}, null);
            }
        }

        if (operation == ClientOperation.UPDATE) {
            if (!isEmpty(id) && !isEmpty(text) && dao.sameTextAlreadyExists(id, text)) {
                errors.rejectValue("id", "008", new String[]{NAME}, null);
            }
            if (!isEmpty(id)) {
                String currentCommentary = dao.getCurrentCommentary(id);
                if (currentCommentary.equals(message.getCommentary())) {
                    errors.rejectValue("commentary", "015", new String[] {NAME}, null);
                }
            }
        }

        if (operation == ClientOperation.DELETE) {
            if (!isEmpty(id) && dao.isUsed(id)) {
                errors.rejectValue("id", "006", new String[]{NAME}, null);
            }
            if (!isEmpty(id)) {
                String currentCommentary = dao.getCurrentCommentary(id);
                if (currentCommentary.equals(message.getCommentary())) {
                    errors.rejectValue("commentary", "019", new String[]{NAME}, null);
                }
            }
        }
    }

}
