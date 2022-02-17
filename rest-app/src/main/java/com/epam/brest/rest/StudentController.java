package com.epam.brest.rest;



import com.epam.brest.model.Course;
import com.epam.brest.model.Student;

import com.epam.brest.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@Tag(name="Student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/students")
    @Operation(summary = "Get all students", responses = {
            @ApiResponse(description = "Get all students success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public final Collection<Student> students() {
        return studentService.findAll();
    }

    @GetMapping(value = "/students/{id}")
    @Operation(summary = "Get student", responses = {
            @ApiResponse(description = "Get student success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public final Student getStudentById(@PathVariable Integer id){
        Student student = studentService.getStudentById(id);
        return student;
    }

    @PostMapping(path = "/students", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create student", responses = {
            @ApiResponse(description = "Create student success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public ResponseEntity<Integer> createStudent(@RequestBody Student student) {

        Integer id = studentService.create(student);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/students", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Update student", responses = {
            @ApiResponse(description = "Update student success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public ResponseEntity<Integer> updateStudent(@RequestBody Student student) {

        int result = studentService.update(student);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/students/{id}", produces = {"application/json"})
    @Operation(summary = "Delete student", responses = {
            @ApiResponse(description = "Delete student success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public ResponseEntity<Integer> deleteStudent(@PathVariable Integer id) {

        int result = studentService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/students/filter" ,produces = {"application/json"})
    @Operation(summary = "Filter student", responses = {
            @ApiResponse(description = "Filter student success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public ResponseEntity<List<Student>> filterStudentByBirthDate(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        List<Student> students = studentService.filterStudentByBirthDate(startDate, endDate);
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

}
