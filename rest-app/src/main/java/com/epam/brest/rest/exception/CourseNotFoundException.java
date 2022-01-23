package com.epam.brest.rest.exception;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(Integer id) {
        super("Course not found for id: " + id);
    }
}
