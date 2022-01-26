package com.epam.brest.service.impl;


import com.epam.brest.dao.StudentDao;

import com.epam.brest.model.Student;
import com.epam.brest.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
