package com.epam.brest.service.impl;


import com.epam.brest.dao.StudentDao;

import com.epam.brest.model.Student;
import com.epam.brest.service.StudentService;
import com.epam.brest.service.exceptions.StudentNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    @Transactional
    public Integer create(Student student) {
        return this.studentDao.create(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return this.studentDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Integer studentId) {
        try{
            return  this.studentDao.getStudentById(studentId);
        } catch (EmptyResultDataAccessException e){
            throw new StudentNotFoundException(studentId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer update(Student student) {
        try{
            return  this.studentDao.update(student);
        } catch (EmptyResultDataAccessException e){
            throw  new StudentNotFoundException(student.getStudentId());
        }
    }

    @Override
    @Transactional
    public Integer delete(Integer studentId) {
        return this.studentDao.delete(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        return this.studentDao.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> filterStudentByBirthDate(LocalDate startDate, LocalDate endDate) {
        return this.studentDao.filterStudentByBirthDate(startDate,endDate);
    }
}
