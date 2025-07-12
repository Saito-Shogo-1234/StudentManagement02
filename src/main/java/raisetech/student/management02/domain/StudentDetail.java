package raisetech.student.management02.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management02.data.Student;
import raisetech.student.management02.data.StudentCourses;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentCourses> studentCourses;
}
