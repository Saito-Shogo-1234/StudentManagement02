package raisetech.student.management02.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management02.data.CourseStatus;
import raisetech.student.management02.data.StudentCourse;

@Schema(description = "受講生コース情報の詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseDetail {

  private StudentCourse studentCourse;
  private CourseStatus courseStatus;
}
