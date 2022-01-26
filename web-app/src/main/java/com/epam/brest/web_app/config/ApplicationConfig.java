package com.epam.brest.web_app.config;

import com.epam.brest.service.CourseDtoService;
import com.epam.brest.service.CourseService;
import com.epam.brest.service.StudentService;
import com.epam.brest.service.rest.CourseDtoServiceRest;
import com.epam.brest.service.rest.CourseServiceRest;
import com.epam.brest.service.rest.StudentServiceRest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class ApplicationConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    CourseDtoService courseDtoService() {
        String url = String.format("%s://%s:%d/course-dtos", protocol, host, port);
        return new CourseDtoServiceRest(url, restTemplate());
    }


    @Bean
    CourseService courseService() {
        String url = String.format("%s://%s:%d/courses", protocol, host, port);
        return new CourseServiceRest(url, restTemplate());
    }

    @Bean
    StudentService studentService() {
        String url = String.format("%s://%s:%d/students", protocol, host, port);
        return new StudentServiceRest(url, restTemplate());
    }
}