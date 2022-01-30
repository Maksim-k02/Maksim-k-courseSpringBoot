package com.epam.brest.rest;



import com.epam.brest.model.Student;
import com.epam.brest.rest.exception.CustomExceptionHandler;
import com.epam.brest.rest.exception.ErrorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static com.epam.brest.model.constants.CourseConstants.COURSE_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.epam.brest.model.constants.StudentConstants.NAME_SIZE;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
class StudentControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentControllerIT.class);

    public static final String STUDENTS_ENDPOINT = "/students";

    @Autowired
    private StudentController studentController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private MockMvc mockMvc;

    MockMvcStudentService studentService =  new MockMvcStudentService();

    @BeforeEach
    public void before(){
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void shouldFindAllStudents() throws Exception {
        LOGGER.debug("shouldFindAllStudents()");
        // GIVEN
        Student testStudent = testedStudent(1);
        Integer id = studentService.create(testStudent);
        assertNotNull(id);
        // WHEN
        List<Student> students = studentService.findAll();
        // THEN
        assertNotNull(students);
        assertTrue(students.size() > 0);

    }

    @Test
    void getStudentById() throws Exception {
        LOGGER.debug("getStudentById()");
        // GIVEN
        Student testStudent = testedStudent(5);
        Integer id = studentService.create(testStudent);
        assertNotNull(id);
        // WHEN
        Optional<Student> optionalStudent = studentService.findById(id);
        // THEN
        assertTrue(optionalStudent.isPresent());
        assertEquals(optionalStudent.get().getStudentId(), id);
        assertEquals(testStudent.getStudentName(),optionalStudent.get().getStudentName());
        assertEquals(testStudent.getEmail(),optionalStudent.get().getEmail());
        assertEquals(testStudent.getCourseNumber(),optionalStudent.get().getCourseNumber());
        assertEquals(testStudent.getStudentDate(),optionalStudent.get().getStudentDate());
        assertEquals(testStudent.getCourseId(),optionalStudent.get().getCourseId());
    }

    @Test
    void createStudent() throws Exception {
        LOGGER.debug("createStudent()");
        Student testStudent = testedStudent(5);
        Integer id = studentService.create(testStudent);
        assertNotNull(id);
    }


    @Test
    void updateStudent() throws Exception{
        LOGGER.debug("updateStudent()");

        // GIVEN
        Student testStudent = testedStudent(4);
        Integer id = studentService.create(testStudent);
        assertNotNull(id);

        Optional<Student> optionalStudent = studentService.findById(id);
        assertTrue(optionalStudent.isPresent());

        optionalStudent.get().setStudentName("Vasia");
        optionalStudent.get().setEmail("vasia@mail.ru" );
        optionalStudent.get().setCourseNumber(3);
        optionalStudent.get().setStudentDate(LocalDate.of(1996,3,19));
        optionalStudent.get().setCourseId(2);
        // WHEN
        int result = studentService.update(optionalStudent.get());
        // THEN
        assertTrue(1 == result);

        Optional<Student> updatedOptionalStudent = studentService.findById(id);
        assertTrue(updatedOptionalStudent.isPresent());
        assertEquals(updatedOptionalStudent.get().getStudentId(), id);
        assertEquals(updatedOptionalStudent.get().getStudentName(),optionalStudent.get().getStudentName());
        assertEquals(updatedOptionalStudent.get().getEmail(),optionalStudent.get().getEmail());
        assertEquals(updatedOptionalStudent.get().getCourseNumber(),optionalStudent.get().getCourseNumber());
        assertEquals(updatedOptionalStudent.get().getStudentDate(),optionalStudent.get().getStudentDate());
        assertEquals(updatedOptionalStudent.get().getCourseId(),optionalStudent.get().getCourseId());
    }

    @Test
    void deleteStudent() throws Exception{
        LOGGER.debug("deleteStudent()");
        // GIVEN
        Student testStudent = testedStudent(4);
        Integer id = studentService.create(testStudent);
        assertNotNull(id);

        List<Student> students = studentService.findAll();
        assertNotNull(students);
        // WHEN
        int result = studentService.delete(id);
        // THEN
        assertTrue(1 == result);

        List<Student> afterDeleteStudent = studentService.findAll();
        assertNotNull(afterDeleteStudent);
        assertTrue(students.size()-1 == afterDeleteStudent.size());

    }

    class MockMvcStudentService {

        public List<Student> findAll() throws Exception{
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(STUDENTS_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Student>>() {
            });
        }

        public Optional<Student> findById(Integer id) throws Exception {
            LOGGER.debug("findById({})", id);
            MockHttpServletResponse response = mockMvc.perform(get(STUDENTS_ENDPOINT + "/" + id)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Student.class));
        }

        public Integer create(Student student) throws Exception {
            LOGGER.debug("create({})", student);
            String json = objectMapper.writeValueAsString(student);
            MockHttpServletResponse response = mockMvc.perform(post(STUDENTS_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int update(Student student) throws Exception {
            LOGGER.debug("update({})", student);
            MockHttpServletResponse response =
                    mockMvc.perform(put(STUDENTS_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(student))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int delete(Integer studentId) throws Exception {

            LOGGER.debug("delete(id:{})", studentId);
            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.delete(new StringBuilder(STUDENTS_ENDPOINT).append("/")
                                            .append(studentId).toString())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

    }

    private Student testedStudent(int index) {
        Student testStudent = new Student();
        LocalDate studentDate = LocalDate.parse("1993-02-02");
        testStudent.setStudentId(index);
        testStudent.setStudentName("Maksim");
        testStudent.setEmail("maksim@mail.ru" + index);
        testStudent.setCourseNumber(1);
        testStudent.setStudentDate(studentDate.minusDays(index));
        testStudent.setCourseId(1);

        return testStudent;
    }
}