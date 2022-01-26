package com.epam.brest.rest;



import com.epam.brest.model.Student;

import com.epam.brest.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(path = "/students", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createStudent(@RequestBody Student student) {

        Integer id = studentService.create(student);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


}
