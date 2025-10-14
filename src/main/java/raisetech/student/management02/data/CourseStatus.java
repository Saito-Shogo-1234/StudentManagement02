package raisetech.student.management02.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "申込状況")
@Getter
@Setter
public class CourseStatus {

  private int id;
  private int studentCourseId;
  private String statusName;
}
