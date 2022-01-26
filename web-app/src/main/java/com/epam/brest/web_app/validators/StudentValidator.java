package com.epam.brest.web_app.validators;


import com.epam.brest.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.epam.brest.model.constants.CourseConstants.COURSE_NAME_SIZE;

@Component
public class StudentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,"firstName", "firstName.empty");
        Student student = (Student) target;

        if (StringUtils.hasLength(student.getFirstName())
                && student.getFirstName().length() > COURSE_NAME_SIZE) {
            errors.rejectValue("firstName", "firstName.maxSize");
        }
    }
}
