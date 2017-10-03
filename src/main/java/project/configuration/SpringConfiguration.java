package project.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("project.controller")
@PropertySource("classpath:database.properties")
public class SpringConfiguration {

    @Value("${host}")
    private static String host;

    @Value("${port}")
    private static String port;

    @Value("${login}")
    private static String login;

    @Value("${password}")
    private static String password;

    @Value("${database}")
    private static String database;

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertyPlaceholderConfigurer();
    }

    @Bean
    public static DataSource dataSource() {
        return new DriverManagerDataSource(String.format("jdbc:postgresql://%s/%s/%s", host, port, database), login, password);
    }

}
