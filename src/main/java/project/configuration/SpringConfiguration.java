package project.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan("project")
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
public class SpringConfiguration {

    @Value("${host}")
    private String host;

    @Value("${port}")
    private String port;

    @Value("${login}")
    private String login;

    @Value("${password}")
    private String password;

    @Value("${database}")
    private String database;

    @Value("${schema}")
    private String schema;

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(String.format("jdbc:postgresql://%s:%s/%s?currentSchema=%s", host, port, database, schema), login, password);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Bean
    public PasswordEncoder pe() {
        return new BCryptPasswordEncoder();
    }

}
