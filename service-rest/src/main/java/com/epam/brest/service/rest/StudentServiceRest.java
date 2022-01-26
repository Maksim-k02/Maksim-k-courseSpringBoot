package com.epam.brest.service.rest;

import com.epam.brest.model.Student;
import com.epam.brest.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StudentServiceRest implements StudentService {

    private String url;

    private RestTemplate restTemplate;

    public StudentServiceRest() {
    }

    public StudentServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public Integer create(Student student) {
        ResponseEntity responseEntity = restTemplate.postForEntity(url, student, Integer.class);
        return (Integer) responseEntity.getBody();
    }
}
