package aptekaproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class AptekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AptekaApplication.class, args);
    }
}
