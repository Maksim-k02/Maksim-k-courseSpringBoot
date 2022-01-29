package com.epam.brest.web_app.validators;


import com.epam.brest.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import static com.epam.brest.model.constants.StudentConstants.EMAIL_SIZE;
import static com.epam.brest.model.constants.StudentConstants.NAME_SIZE;

@Component
public class StudentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty");
        Student student = (Student) target;

        if (StringUtils.hasLength(student.getEmail())
                && student.getEmail().length() > EMAIL_SIZE) {
            errors.rejectValue("email", "email.maxSize");
        }

        if (StringUtils.hasLength(student.getStudentName())
                && student.getStudentName().length() > NAME_SIZE) {
            errors.rejectValue("studentName", "studentName.maxSize");
        }
        if (student.getCourseNumber()>6 || student.getCourseNumber()<1) {
            errors.rejectValue("courseNumber", "courseNumber.size");
        }

        if (student.getStudentDate() == null) {
            errors.rejectValue("studentDate", "studentDate.empty");
        }
    }


}
