package com.epam.brest.service.impl;

import com.epam.brest.model.Student;
import com.epam.brest.service.StudentService;
import com.epam.brest.service.config.ServiceTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfig.class})
@PropertySource({"classpath:dao.properties"})
@Transactional
class StudentServiceImplIT {

    private final Logger LOGGER = LogManager.getLogger(StudentServiceImplIT.class);
    private static Student testStudent;

    @Autowired
    StudentService studentService;

    @BeforeEach
    void setUp(){
        assertNotNull(studentService);
        testStudent = new Student("Filip","some email", 1, 2, LocalDate.of(2000,12,12));
    }

    @Test
    void create() {
        LOGGER.debug("create()");
        Integer sizeBefore = studentService.count();
        Integer testStudentId = studentService.create(testStudent);
        assertNotNull(testStudentId);
        assertEquals(sizeBefore,studentService.count() - 1);
    }

    @Test
    void findAll() {
        LOGGER.debug("findAll()");
        assertNotNull(studentService.findAll());
        List<Student> students = studentService.findAll();
        assertNotNull(students);
        assertFalse(students.isEmpty());
    }

    @Test
    void getStudentById() {
        LOGGER.debug("getStudentById()");
        Integer studentId = studentService.create(testStudent);
        assertNotNull(studentId);
        Student studentGetById = studentService.getStudentById(studentId);
        assertEquals(testStudent.getStudentName(),studentGetById.getStudentName());
        assertEquals(testStudent.getEmail(),studentGetById.getEmail());
        assertEquals(testStudent.getCourseId(),studentGetById.getCourseId());
        assertEquals(testStudent.getCourseNumber(),studentGetById.getCourseNumber());
    }

    @Test
    void update() {
        LOGGER.debug("update()");
        Integer testStudentId = 2;
        Student studentSrc = studentService.getStudentById(testStudentId);
        studentSrc.setEmail(studentSrc.getEmail() + "test");
        studentSrc.setStudentDate(studentSrc.getStudentDate().minusDays(1));
        studentSrc.setCourseId(studentSrc.getCourseId() + 1);

        studentService.update(studentSrc);
        Student studentDst = studentService.getStudentById(studentSrc.getStudentId());

        assertEquals(studentSrc.getEmail(),studentDst.getEmail());
        assertEquals(studentSrc.getCourseId(),studentDst.getCourseId());
        assertEquals(studentSrc.getStudentDate(),studentDst.getStudentDate());
    }

    @Test
    void delete() {
        LOGGER.debug("delete()");
        List<Student> students = studentService.findAll();
        assertNotNull(students);
        assertFalse(students.isEmpty());
        studentService.delete(students.get(0).getStudentId());
        assertEquals(studentService.findAll().size(),students.size() - 1);
    }

}