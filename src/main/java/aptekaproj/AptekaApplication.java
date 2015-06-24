package aptekaproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/*
@SpringBootApplication
public class AptekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AptekaApplication.class, args);
    }
}
*/
@SpringBootApplication
public class AptekaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AptekaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AptekaApplication.class);
    }

}
