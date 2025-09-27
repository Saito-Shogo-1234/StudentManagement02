package raisetech.student.management02.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management02.data.Student;
import raisetech.student.management02.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 指定した受講生IDで受講生が検索できること() {
    String studentId = "1";
    Student actual = sut.searchStudent(studentId);
    assertThat(actual).isNotNull();
    assertThat(actual.getId()).isEqualTo(studentId);
    assertThat(actual.getName()).isEqualTo("三橋桃子");
  }

  @Test
  void 受講生コース情報の全件検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(5);
    assertThat(actual.get(0).getCourseName()).isEqualTo("Javaプログラミング基礎");
  }

  @Test
  void 指定した受講生IDに紐づく受講生コース情報が検索できること() {
    String studentId = "1";
    List<StudentCourse> actual = sut.searchStudentCourse(studentId);
    assertThat(actual).isNotEmpty();
    assertThat(actual.get(0).getStudentId()).isEqualTo(studentId);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setName("三橋桃子");
    student.setName("ミハシモモコ");
    student.setName("モモカン");
    student.setName("mihashi.momoko@example.com");
    student.setName("京都");
    student.setName("19");
    student.setName("女性");
    student.setName("");
    student.setName("false");

    sut.registerStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    StudentCourse course = new StudentCourse();
    course.setStudentId("1");
    course.setCourseName("Java");
    course.setCourseStartAt(LocalDateTime.now());
    course.setCourseEndAt(LocalDateTime.now().plusMonths(6));

    sut.registerStudentCourse(course);

    List<StudentCourse> actual = sut.searchStudentCourse("1");

    assertThat(actual).isNotEmpty();
    assertThat(actual.get(0).getCourseName()).isEqualTo("Javaプログラミング基礎");
  }

  @Test
  void 受講生の情報を更新できること() {
    Student student = sut.searchStudent("1");
    student.setName("三橋桃子 更新");
    student.setEmail("mihashi.updated@example.com");

    sut.updateStudent(student);

    Student actual = sut.searchStudent("1");
    assertThat(actual.getName()).isEqualTo("三橋桃子 更新");
    assertThat(actual.getEmail()).isEqualTo("mihashi.updated@example.com");
  }

  @Test
  void 受講生コース情報のコース名を更新できること() {
    List<StudentCourse> courses = sut.searchStudentCourse("1");
    StudentCourse course = courses.get(0);
    course.setCourseName("Python");

    sut.updateStudentCourse(course);

    StudentCourse actual = sut.searchStudentCourse("1").get(0);
    assertThat(actual.getCourseName()).isEqualTo("Python");
  }
}