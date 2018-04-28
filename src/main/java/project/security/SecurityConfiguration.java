package project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /** Cookie (не HttpOnly) служит для понимания (на стороне js) залогинился пользователь или нет. */
    private static final String LOGGED = "LOGGED";

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        super(true);
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Запросы которые не изменяют состояние ресурсов (в терминологии REST) должны выполняться минуя механизмы аутентификации и авторизации.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.GET, "/resources/**", "/login", "/")

                //Запросы данных по конкретной модели, например, entity/1.
                .antMatchers(HttpMethod.GET, "/entity/*")
                .antMatchers(HttpMethod.GET, "/operation/*")
                .antMatchers(HttpMethod.GET, "/message/*")
                .antMatchers(HttpMethod.GET, "/validation/*")
                .antMatchers(HttpMethod.GET, "/tag/*")

                .antMatchers(HttpMethod.GET, "/**/change/**") //Список изменений
                .antMatchers(HttpMethod.GET, "/**/favicon.ico")
                .antMatchers(HttpMethod.POST, "/user/register")
                .antMatchers(HttpMethod.POST, "/*/query"); // Поисковые запросы.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.securityContext();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.headers();

        http.headers().frameOptions().sameOrigin();

        http.apply(new AdvancedDefaultLoginPageConfigurer<>());

        http.authorizeRequests().anyRequest().authenticated();

        http.formLogin()
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

        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            if (authException instanceof AuthenticationCredentialsNotFoundException) {
                Cookie cookie = new Cookie(LOGGED, null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);

                response.sendError(401);
            } else {
                response.sendError(403);
            }
        });
    }

}
