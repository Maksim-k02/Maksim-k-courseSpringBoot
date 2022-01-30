package com.epam.brest.dao;

import com.epam.brest.model.Student;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJdbcTest
@Import({StudentDaoJDBCImpl.class})
@PropertySource({"classpath:dao.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
class StudentDaoJDBCImplTest {

    private final Logger LOGGER = LogManager.getLogger(StudentDaoJDBCImplTest.class);

    private StudentDaoJDBCImpl studentDaoJDBC;

    @Autowired(required=true)
    public StudentDaoJDBCImplTest(StudentDao studentDaoJDBC) {
        this.studentDaoJDBC = (StudentDaoJDBCImpl) studentDaoJDBC;
    }

    @Test
    void findAll() {
        LOGGER.debug("Execute test: findAll()");
        assertNotNull(studentDaoJDBC);
        assertNotNull(studentDaoJDBC.findAll());
    }

    @Test
    void getStudentById() {
        LOGGER.debug("Execute test: getStudentById()");
        List<Student> students = studentDaoJDBC.findAll();
        if (students.size()==0){
            studentDaoJDBC.create(new Student("TEST STUDENT","test@mail.ru",1,1, LocalDate.of(2021,12,25)));
            students = studentDaoJDBC.findAll();
        }
        Student studentSrc = students.get(0);
        Student studentDts = studentDaoJDBC.getStudentById(studentSrc.getStudentId());
        assertEquals(studentSrc.getStudentName(),studentDts.getStudentName());
    }

    @Test
    void create() {
        LOGGER.debug("Execute test: create()");
        assertNotNull(studentDaoJDBC);
        int studentSizeBefore = studentDaoJDBC.count();
        Student student = new Student("TEST STUDENT","test@mail.ru",1,1, LocalDate.of(2021,12,25));
        Integer newStudentId = studentDaoJDBC.create(student);
        assertNotNull(newStudentId);
        assertEquals(studentSizeBefore,studentDaoJDBC.count()-1);
    }

    @Test
    void update() {
        LOGGER.debug("Execute test: update()");
        List<Student> students = studentDaoJDBC.findAll();
        if (students.size()==0){
            studentDaoJDBC.create(new Student("TEST STUDENT","test@mail.ru",1,1, LocalDate.of(2021,12,25)));
            students = studentDaoJDBC.findAll();
        }
        Student studentSrc = students.get(0);
        studentSrc.setStudentName(studentSrc.getStudentName() + "TEST");
        studentDaoJDBC.update(studentSrc);

        Student studentDts = studentDaoJDBC.getStudentById(studentSrc.getStudentId());
        assertEquals(studentSrc.getStudentName(),studentDts.getStudentName());
    }

    @Test
    void delete() {
        LOGGER.debug("Execute test: delete()");
        studentDaoJDBC.create(new Student("TEST DELETE","test@mail.ru",1,1, LocalDate.of(2021,12,25)));
        List<Student> students = studentDaoJDBC.findAll();
        studentDaoJDBC.delete(students.get(students.size()-1).getStudentId());
        assertEquals(students.size()-1, studentDaoJDBC.findAll().size());
    }
}