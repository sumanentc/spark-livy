package ai.cuddle.livy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by suman.das on 12/27/17.
 */
@SpringBootApplication
@ComponentScan("ai.cuddle.livy.*")
@PropertySource("classpath:application.properties")
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
