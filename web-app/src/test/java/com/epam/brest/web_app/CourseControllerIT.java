package com.epam.brest.web_app;

import com.epam.brest.model.Course;
import com.epam.brest.model.dto.CourseDto;
import com.epam.brest.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;

import static com.epam.brest.model.constants.CourseConstants.COURSE_NAME_SIZE;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@Disabled
class CourseControllerIT {

    private static final String COURSE_DTOS_URL = "http://localhost:8088/course-dtos";
    private static final String COURSES_URL = "http://localhost:8088/courses";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper =  new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldReturnCoursesPage() throws Exception {

        CourseDto d1 = createCourseDto(1, "JAVA1", BigDecimal.valueOf(3));
        CourseDto d2 = createCourseDto(2, "MAVEN2", BigDecimal.valueOf(2));
        CourseDto d3 = createCourseDto(3, "PHP3", null);
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(COURSE_DTOS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(d1, d2, d3)))
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/courses")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("courses"))
                .andExpect(model().attribute("courses", hasItem(
                        allOf(
                                hasProperty("courseId", is(d1.getCourseId())),
                                hasProperty("courseName", is(d1.getCourseName())),
                                hasProperty("countStudent", is(d1.getCountStudent()))
                        )
                )))
//                .andExpect(model().attribute("departments", hasItem(
//                        allOf(
//                                hasProperty("departmentId", is(d2.getDepartmentId())),
//                                hasProperty("departmentName", is(d2.getDepartmentName())),
//                                hasProperty("avgSalary", is(d2.getAvgSalary()))
//                        )
//                )))
//                .andExpect(model().attribute("departments", hasItem(
//                        allOf(
//                                hasProperty("departmentId", is(d3.getDepartmentId())),
//                                hasProperty("departmentName", is(d3.getDepartmentName())),
//                                hasProperty("avgSalary", isEmptyOrNullString())
//                        )
//                )))
        ;

        mockServer.verify();
    }
    @Test
    void shouldAddCourse() throws Exception {
        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(COURSES_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        Course course = new Course("Java");

        // THEN
        //Integer newDepartmentId = departmentService.create(department);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/course")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("courseName", course.getCourseName())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/courses"))
                .andExpect(redirectedUrl("/courses"));


        // VERIFY
        mockServer.verify();
    }

    @Test
    void shouldFailAddCourseOnEmptyName() throws Exception {
        // WHEN
        Course course = new Course("");

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/course")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("courseName", course.getCourseName())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("course"))
                .andExpect(
                        model().attributeHasFieldErrors(
                                "course", "courseName"
                        )
                );
    }

    @Test
    public void shouldOpenEditCoursePageById() throws Exception {
        Course d = createCourse(1, "Maven");
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(COURSES_URL + "/" + d.getCourseId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(d))
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/course/1")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("course"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("course", hasProperty("courseId", is(1))))
                .andExpect(model().attribute("course", hasProperty("courseName", is("Maven"))));
    }

    @Test
    public void shouldUpdateCourseAfterEdit() throws Exception {

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(COURSES_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        String testName = RandomStringUtils.randomAlphabetic(COURSE_NAME_SIZE);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/course/1")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("courseId", "1")
                                .param("courseName", testName)
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/courses"))
                .andExpect(redirectedUrl("/courses"));

        mockServer.verify();
    }

    @Test
    public void shouldDeleteCourse() throws Exception {

        int id = 3;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(COURSES_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/course/3/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/courses"))
                .andExpect(redirectedUrl("/courses"));

        mockServer.verify();
    }

    private CourseDto createCourseDto(int id, String name, BigDecimal countStudent) {
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseId(id);
        courseDto.setCourseName(name);
        courseDto.setCountStudent(countStudent);
        return courseDto;
    }

    private Course createCourse(int id, String name) {
        Course course = new Course();
        course.setCourseId(id);
        course.setCourseName(name);
        return course;
    }
}