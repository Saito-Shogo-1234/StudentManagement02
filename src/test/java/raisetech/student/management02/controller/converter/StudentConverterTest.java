package raisetech.student.management02.controller.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student.management02.data.Student;
import raisetech.student.management02.data.StudentCourse;
import raisetech.student.management02.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void setUp() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生1人にコースが1つ紐づく場合_正しく変換されること() {
    Student student = new Student();
    student.setId("1");
    student.setName("後藤あいり");

    StudentCourse course = new StudentCourse();
    course.setStudentId("1");
    course.setCourseName("Java");

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(course);

    List<StudentDetail> result = sut.convertStudentDetails(studentList, studentCourseList);

    assertEquals(1, result.size());
    assertEquals("1", result.get(0).getStudent().getId());
    assertEquals("Java", result.get(0).getStudentCourseList().get(0).getCourseName());
  }

  @Test
  void 受講生1人に複数コースが紐づく場合_全てマッピングされること() {
    Student student = new Student();
    student.setId("2");

    StudentCourse course1 = new StudentCourse();
    course1.setStudentId("2");
    course1.setCourseName("Spring");

    StudentCourse course2 = new StudentCourse();
    course2.setStudentId("2");
    course2.setCourseName("MySQL");

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(course1, course2);

    List<StudentDetail> result = sut.convertStudentDetails(studentList, studentCourseList);

    assertEquals(1, result.size());
    assertEquals(2, result.get(0).getStudentCourseList().size());
  }

  @Test
  void コースが存在しない場合_空のリストになること() {
    Student student = new Student();
    student.setId("3");

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = new ArrayList<>();

    List<StudentDetail> result = sut.convertStudentDetails(studentList, studentCourseList);

    assertEquals(1, result.size());
    assertTrue(result.get(0).getStudentCourseList().isEmpty());
  }
}
