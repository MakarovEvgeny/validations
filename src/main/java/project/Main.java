package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import project.configuration.SpringConfiguration;

@SpringBootApplication
@Import(SpringConfiguration.class)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

}
