package com.epam.brest.model.dto;

public class StudentDto {
    private Integer studentId;

    private String firstName;

    private String lastName;

    private String email;

    private Integer courseNumber;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(final Integer studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
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

    public StudentDto(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "StudentDto{"
                + "studentId=" + studentId
                + ", firstName='" + firstName
                + '}';
    }
}
