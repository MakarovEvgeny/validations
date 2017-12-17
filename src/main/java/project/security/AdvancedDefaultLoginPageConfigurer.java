package project.security;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

/**
 * Класс-конфиг для добавление фильтра (предоставляет форму для ввода логина/пароля) одновременно с собственным обработчиком ошибок связанных с безопасностью.
 * Свой обработчик нужен для того чтобы не было редиректа на страницу аутентификации в случае если сессия, например, истекла.
 * Фильтр настраивается {@link org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer#initDefaultLoginFilter здесь}.
 *
 * @see org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer
 * @see org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
 */
public class AdvancedDefaultLoginPageConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractHttpConfigurer<DefaultLoginPageConfigurer<H>, H> {

    private DefaultLoginPageGeneratingFilter loginPageGeneratingFilter = new DefaultLoginPageGeneratingFilter();

    @Override
    public void init(H http) throws Exception {
        http.setSharedObject(DefaultLoginPageGeneratingFilter.class,
                loginPageGeneratingFilter);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(H http) throws Exception {
        if (loginPageGeneratingFilter.isEnabled()) {
            loginPageGeneratingFilter = postProcess(loginPageGeneratingFilter);
            http.addFilter(loginPageGeneratingFilter);
        }
    }

}
