package project.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /** Cookie (не HttpOnly) служит для понимания (на стороне js) залогинился пользователь или нет. */
    private static final String LOGGED = "LOGGED";

    /**
     * Запросы которые не изменяют состояние ресурсов (в терминологии REST) должны выполняться минуя механизмы аутентификации и авторизации.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.GET, "/resources/**", "/login", "/")
                .antMatchers(HttpMethod.POST, "/*/query"); // Поисковые запросы.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable()
            .formLogin()
                .failureHandler((request, response, exception) -> {

                    Cookie cookie = new Cookie(LOGGED, null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                })
                .successHandler((request, response, authentication) -> {
                    Cookie cookie = new Cookie(LOGGED, "true");
                    cookie.setMaxAge(3600*24);
                    response.addCookie(cookie);
                });

        http.logout()
                .deleteCookies("JSESSIONID", LOGGED)
                .logoutSuccessHandler((request, response, authentication) -> {});
    }

}
