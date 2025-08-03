package raisetech.student.management02.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management02.data.Student;
import raisetech.student.management02.data.StudentsCourses;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentCourses;

  private boolean cancelUpdate;

  public StudentDetail() {
    this.student = new Student();
    this.studentCourses = new ArrayList<>();
  }
}
