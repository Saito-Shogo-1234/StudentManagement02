package raisetech.student.management02.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  private int id;
  private int studentId;
  private String courseName;
  private LocalDateTime courseStartAt;
  private LocalDateTime courseEndAt;
}
