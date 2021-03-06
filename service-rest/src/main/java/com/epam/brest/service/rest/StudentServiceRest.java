package com.epam.brest.service.rest;

import com.epam.brest.model.Student;
import com.epam.brest.service.StudentService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public List<Student> findAll() {
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Student>) responseEntity.getBody();
    }

    @Override
    public Student getStudentById(Integer studentId) {
        ResponseEntity <Student> responseEntity = restTemplate.getForEntity(url + "/" + studentId, Student.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer update(Student student) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Student> entity =  new HttpEntity<>(student, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer delete(Integer studentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Student> entity =  new HttpEntity<>(headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url + "/" + studentId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer count() {

        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(url + "/count" , Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public List<Student> filterStudentByBirthDate(LocalDate startDate, LocalDate endDate) {
        ResponseEntity <List<Student>> responseEntity = restTemplate.exchange(
                url + "/filter?startDate={startDate}&endDate={endDate}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                startDate, endDate);
        return  responseEntity.getBody();
    }
}
