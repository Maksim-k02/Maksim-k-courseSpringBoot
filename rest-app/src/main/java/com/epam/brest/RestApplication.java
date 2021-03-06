package com.epam.brest;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:dao.properties"})
public class RestApplication extends SpringBootServletInitializer {

    private static final Logger logger = LogManager.getLogger(RestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    public OpenAPI openAPIConfig(){
        return new OpenAPI().info(apiInfo());
    }

    public Info apiInfo(){
        Info info = new Info();
        info.title("Course CODE API")
                .description("Course project Code System Swagger Open API")
                .version("v1.0.0");
        return info;
    }

}
