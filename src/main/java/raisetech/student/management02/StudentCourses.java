package raisetech.student.management02;


import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourses {

 private int id;
 private int studentId;
 private String courseName;
 private Timestamp courseStartAt;
 private Timestamp courseEndAt;
}
