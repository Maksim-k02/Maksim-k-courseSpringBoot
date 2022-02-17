package com.epam.brest.web_app;



import com.epam.brest.model.Student;
import com.epam.brest.service.CourseService;
import com.epam.brest.service.StudentService;

import com.epam.brest.web_app.validators.StudentValidator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    private final StudentValidator studentValidator;

    private final CourseService courseService;

    public StudentController(StudentService studentService, StudentValidator studentValidator, CourseService courseService){
        this.studentService=studentService;
        this.studentValidator=studentValidator;
        this.courseService = courseService;
    }



    /**
     * Goto student list page.
     *
     * @return view name
     */
    @GetMapping(value = "/students")
    public final String students(Model model) {
       model.addAttribute("courses", courseService.findAll());
        model.addAttribute("students", studentService.findAll());
        return "students";
    }

    /**
     * Goto edit students page.
     *
     * @return view name
     */
    @GetMapping(value = "/student/{id}")
    public final String gotoEditStudentPage(@PathVariable Integer id, Model model) {
        model.addAttribute("isNew",false);
        model.addAttribute("student", studentService.getStudentById(id));
        model.addAttribute("courses", courseService.findAll());
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
        model.addAttribute("courses",courseService.findAll());
        return "student";
    }

    /**
     * Persist new student into persistence storage.
     *
     * @param student new student with filled data.
     * @return view name
     */

    @PostMapping(value = "/student")
    public String addStudent(Student student, BindingResult result, Model model) {

        studentValidator.validate(student, result);
        if (result.hasErrors()){
            model.addAttribute("isNew", true);
            model.addAttribute("courses", courseService.findAll());
            return "student";
        } else {
            this.studentService.create(student);
        }
        return "redirect:/students";
    }

    @PostMapping(value = "/student/{id}")
    public String updateStudent(Student student, BindingResult result, Model model){
        studentValidator.validate(student, result);
        if (result.hasErrors()){
            model.addAttribute("isNew", false);
            model.addAttribute("courses", courseService.findAll());
            return "student";
        } else {
            this.studentService.update(student);
        }
        return "redirect:/students";
    }

    /**
     * Delete student.
     *
     * @return view name
     */
    @GetMapping(value = "/student/{id}/delete")
    public final String deleteStudentById(@PathVariable Integer id, Model model) {

        studentService.delete(id);
        return "redirect:/students";
    }

    @GetMapping(value = "/students/filter")
    public final String filterRepairByPreferenceDate(@RequestParam("startDate")
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                     @RequestParam("endDate")
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                     Model model) {

        List<Student> students = studentService.filterStudentByBirthDate(startDate,endDate);
        model.addAttribute("students",students);
        model.addAttribute("courses",courseService.findAll());
        return "students";
    }
}
