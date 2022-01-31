package com.epam.brest.service.config;


import com.epam.brest.dao.*;
import com.epam.brest.service.CourseDtoService;
import com.epam.brest.service.CourseService;
import com.epam.brest.service.StudentService;
import com.epam.brest.service.impl.CourseDtoServiceImpl;
import com.epam.brest.service.impl.CourseServiceImpl;
import com.epam.brest.service.impl.StudentServiceImpl;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceTestConfig extends SpringJdbcConfig {

    @Bean
    CourseDtoDao courseDtoDao(){
        return new CourseDtoDaoJdbc(namedParameterJdbcTemplate());
    }

    @Bean
    CourseDtoService courseDtoService(){
        return new CourseDtoServiceImpl(courseDtoDao());
    }

    @Bean
    CourseDao courseDao(){
        return new CourseDaoJDBCImpl(namedParameterJdbcTemplate());
    }

    @Bean
    CourseService courseService(){
        return new CourseServiceImpl(courseDao());
    }

    @Bean
    StudentDao studentDao(){
        return new StudentDaoJDBCImpl(namedParameterJdbcTemplate());
    }

    @Bean
    StudentService studentService(){
        return new StudentServiceImpl(studentDao());
    }
}
