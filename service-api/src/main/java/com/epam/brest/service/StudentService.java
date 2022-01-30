package com.epam.brest.service;

import com.epam.brest.model.Student;

import java.util.List;

public interface StudentService {
    Integer create (Student student);
    List<Student> findAll();
    Student getStudentById(Integer studentId);
    Integer update(Student student);
    Integer delete(Integer studentId);

    Integer count();
}
