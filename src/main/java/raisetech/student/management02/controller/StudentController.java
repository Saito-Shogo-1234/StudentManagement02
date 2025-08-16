package raisetech.student.management02.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management02.controller.converter.StudentConverter;
import raisetech.student.management02.data.Student;
import raisetech.student.management02.data.StudentsCourses;
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
  public List<StudentDetail> getStudnetList() {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentCourses = service.searchStudentCoursesList();
    return converter.convertStudentDetails(students, studentCourses);
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentCourses(Arrays.asList(new StudentsCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if(result.hasErrors()) {
      return "registerStudent";
    }
    service.registerStudent(studentDetail);
    return "redirect:/studentList";
  }

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    if (studentDetail.getStudent() == null) {
      studentDetail.setStudent(new Student());
    }

    if (studentDetail.isCancelUpdate()) {
      studentDetail.getStudent().setDeleted(true);
    } else {
      studentDetail.getStudent().setDeleted(false);
    }

    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新が完了しました");
  }
}
