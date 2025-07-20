package raisetech.student.management02.domain;

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

  // ★ null防止のためのコンストラクタ追加
  public StudentDetail() {
    this.student = new Student();
  }
}
