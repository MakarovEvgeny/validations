package project.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan("project")
@PropertySource("classpath:database.properties")
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

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(String.format("jdbc:postgresql://%s:%s/%s", host, port, database), login, password);
    }

}
