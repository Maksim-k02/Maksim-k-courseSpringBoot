package com.epam.brest.dao;

import com.epam.brest.model.Student;

import java.time.LocalDate;
import java.util.List;

public interface StudentDao {

    List<Student> findAll();
    Student getStudentById(Integer studentId);
    Integer create(Student student);
    Integer update(Student student);
    Integer delete(Integer studentId);
    Integer count();

    List<Student> filterStudentByBirthDate(LocalDate startDate, LocalDate endDate);

}
