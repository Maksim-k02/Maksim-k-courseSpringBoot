package com.epam.brest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Student {
    private Integer studentId;
    private String studentName;
    private String email;
    private Integer courseNumber;

    private Integer courseId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate studentDate;

    public Student() {
    }


    public Student(String studentName, String email, Integer courseNumber, Integer courseId, LocalDate studentDate) {

        this.studentName = studentName;
        this.email = email;
        this.courseNumber = courseNumber;
        this.courseId = courseId;
        this.studentDate = studentDate;
    }

    public LocalDate getStudentDate() {
        return studentDate;
    }

    public void setStudentDate(LocalDate studentDate) {
        this.studentDate = studentDate;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
