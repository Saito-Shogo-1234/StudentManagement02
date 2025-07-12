package raisetech.student.management02.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management02.controller.converter.StudentConverter;
import raisetech.student.management02.data.Student;
import raisetech.student.management02.data.StudentCourses;
import raisetech.student.management02.domain.StudentDetail;
import raisetech.student.management02.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentCourses> studentCourses = service.searchStudentCoursesList();

    return converter.convertStudentDetails(students, studentCourses);
  }

  @GetMapping("/studentCoursesList")
  public List<StudentCourses> getStudentCoursesList() {
    return service.searchStudentCoursesList();
  }
}
