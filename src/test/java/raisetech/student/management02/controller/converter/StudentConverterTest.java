package raisetech.student.management02.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
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
    Student student = createStudent();

    StudentCourse course = new StudentCourse();
    course.setId("1");
    course.setStudentId("1");
    course.setCourseName("Java");
    course.setCourseStartAt(LocalDateTime.now());
    course.setCourseEndAt(LocalDateTime.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(course);

    List<StudentDetail> result= sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(result.get(0).getStudent()).isEqualTo(student);
    assertThat(result.get(0).getStudentCourseList()).isEqualTo(studentCourseList);
  }

  @Test
  void 紐づいていない受講生コース情報は除外されること() {
    Student student = createStudent();

    StudentCourse course = new StudentCourse();
    course.setId("1");
    course.setStudentId("2");
    course.setCourseName("Java");
    course.setCourseStartAt(LocalDateTime.now());
    course.setCourseEndAt(LocalDateTime.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(course);

    List<StudentDetail> result = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(result.get(0).getStudent()).isEqualTo(student);
    assertThat(result.get(0).getStudentCourseList()).isEmpty();
  }

  private static Student createStudent() {
    Student student = new Student();
    student.setId("1");
    student.setName("三橋桃子");
    student.setName("ミハシモモコ");
    student.setName("モモカン");
    student.setName("mihashi.momoko@example.com");
    student.setName("京都");
    student.setName("19");
    student.setName("女性");
    student.setName("");
    student.setName("false");
    return student;
  }
}
