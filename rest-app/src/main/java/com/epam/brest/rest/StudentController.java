package com.epam.brest.rest;



import com.epam.brest.model.Student;

import com.epam.brest.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/students")
    public final Collection<Student> students() {
        return studentService.findAll();
    }

    @GetMapping(value = "/students/{id}")
    public final Student getStudentById(@PathVariable Integer id){
        Student student = studentService.getStudentById(id);
        return student;
    }

    @PostMapping(path = "/students", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createStudent(@RequestBody Student student) {

        Integer id = studentService.create(student);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/students", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateStudent(@RequestBody Student student) {

        int result = studentService.update(student);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/students/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteStudent(@PathVariable Integer id) {

        int result = studentService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
