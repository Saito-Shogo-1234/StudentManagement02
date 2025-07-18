package raisetech.student.management02.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management02.data.Student;
import raisetech.student.management02.data.StudentCourses;
import raisetech.student.management02.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentCourses> searchStudentCoursesList() {
    return repository.searchStudentsCourses();
  }
}
