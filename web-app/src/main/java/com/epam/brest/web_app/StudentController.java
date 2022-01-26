package com.epam.brest.web_app;



import com.epam.brest.model.Student;
import com.epam.brest.service.StudentService;

import com.epam.brest.web_app.validators.StudentValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    private final StudentService studentService;

    private final StudentValidator studentValidator;

    public StudentController(StudentService studentService, StudentValidator studentValidator){
        this.studentService=studentService;
        this.studentValidator=studentValidator;
    }



    /**
     * Goto student list page.
     *
     * @return view name
     */
    @GetMapping(value = "/students")
    public final String students(Model model) {
        return "students";
    }

    /**
     * Goto edit students page.
     *
     * @return view name
     */
    @GetMapping(value = "/student/{id}")
    public final String gotoEditStudentPage(@PathVariable Integer id, Model model) {
        return "student";
    }

    /**
     * Goto new student page.
     *
     * @return view name
     */
    @GetMapping(value = "/student")
    public final String gotoAddStudentPage(Model model) {
        model.addAttribute("isNew", true);
        model.addAttribute("student", new Student());
        return "student";
    }

    /**
     * Persist new student into persistence storage.
     *
     * @param student new student with filled data.
     * @return view name
     */
    @PostMapping(value = "/student")
    public String addStudent(Student student, BindingResult result) {

        studentValidator.validate(student, result);
        if (result.hasErrors()){
            return "student";
        }
        this.studentService.create(student);
        return "redirect:/students";
    }
}
