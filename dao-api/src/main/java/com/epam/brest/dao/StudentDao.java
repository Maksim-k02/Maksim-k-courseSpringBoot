package com.epam.brest.dao;

import com.epam.brest.model.Student;

import java.util.List;

public interface StudentDao {

    List<Student> findAll();

    Integer create(Student student);
    Integer update(Student student);
    Integer delete(Integer studentId);

}