package com.epam.brest.rest;


import com.epam.brest.dao.CourseDaoJDBCImpl;
import com.epam.brest.model.Course;
import com.epam.brest.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@Tag(name="Course")
public class CourseController {

    private static final Logger logger = LogManager.getLogger(CourseDaoJDBCImpl.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/courses")
    @Operation(summary = "Get all courses", responses = {
            @ApiResponse(description = "Get all course success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public final Collection<Course> courses() {

        logger.debug("courses()");
        return courseService.findAll();
    }

    @GetMapping(value = "/courses/{id}")
    @Operation(summary = "Get course", responses = {
            @ApiResponse(description = "Get course success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public final Course getCourseById(@PathVariable Integer id){

        logger.debug("getCourseById({})", id);
        Course course = courseService.getCourseById(id);
        return course;
    }

    @PostMapping(path = "/courses", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create course", responses = {
            @ApiResponse(description = "Created course success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public ResponseEntity<Integer> createCourse(@RequestBody Course course) {

        logger.debug("createCourse({})", course);
        Integer id = courseService.create(course);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/courses", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Update course", responses = {
            @ApiResponse(description = "Update course success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public ResponseEntity<Integer> updateCourse(@RequestBody Course course) {

        logger.debug("updateCourse({})", course);
        int result = courseService.update(course);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/courses/{id}", produces = {"application/json"})
    @Operation(summary = "Delete course", responses = {
            @ApiResponse(description = "Delete course success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public ResponseEntity<Integer> deleteCourse(@PathVariable Integer id) {

        int result = courseService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
