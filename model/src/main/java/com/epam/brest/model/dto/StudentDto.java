package com.epam.brest.model.dto;

public class StudentDto {
    private Integer studentId;

    private String studentName;



    private String email;

    private Integer courseNumber;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(final Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(final String studentName) {
        this.studentName = studentName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(final Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    public StudentDto() {

    }

    public StudentDto(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "StudentDto{"
                + "studentId=" + studentId
                + ", studentName='" + studentName
                + '}';
    }
}
