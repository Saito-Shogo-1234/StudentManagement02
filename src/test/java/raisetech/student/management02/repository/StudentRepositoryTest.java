package raisetech.student.management02.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import raisetech.student.management02.controller.converter.StudentConverter;
import raisetech.student.management02.data.Student;
import raisetech.student.management02.data.StudentCourse;
import raisetech.student.management02.domain.StudentDetail;
import raisetech.student.management02.service.StudentService;

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
  void 存在しない受講生IDで検索するとnullが返ってくること() {
    String studentId = "999";
    Student actual = sut.searchStudent(studentId);
    assertThat(actual).isNull();
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
  void 存在しない受講生IDに紐づいたコース情報を検索すると空のリストが返ってくること() {
    String studentId = "999";
    List<StudentCourse> actual = sut.searchStudentCourse(studentId);
    assertThat(actual).isEmpty();

  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setName("三橋桃子");
    student.setKanaName("ミハシモモコ");
    student.setNickname("モモカン");
    student.setEmail("mihashi.momoko@example.com");
    student.setArea("京都");
    student.setAge(19);
    student.setSex("女性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 名前が空文字だと登録で例外が発生すること() {
    StudentRepository repository = mock(StudentRepository.class);
    StudentConverter converter = mock(StudentConverter.class);

    StudentService studentService = new StudentService(repository, converter);

    Student student = new Student();
    student.setName(null);
    student.setKanaName("ミハシモモコ");
    student.setNickname("モモカン");
    student.setEmail("mihashi.momoko@example.com");
    student.setArea("京都");
    student.setAge(19);
    student.setSex("女性");
    student.setRemark("");
    student.setDeleted(false);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(Collections.emptyList());

    assertThatThrownBy(() -> studentService.registerStudent(detail))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("名前は必須です");
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
  void 受講生IDが空文字だと例外が発生すること() {
    StudentCourse course = new StudentCourse();
    course.setStudentId("");
    course.setCourseName("Java");
    course.setCourseStartAt(LocalDateTime.now());
    course.setCourseEndAt(LocalDateTime.now().plusMonths(6));

    assertThatThrownBy(() -> sut.registerStudentCourse(course))
        .isInstanceOf(DataIntegrityViolationException.class)
        .hasMessageContaining("STUDENT_ID");
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
  void 存在しない受講生IDを指定しても例外は発生しないこと() {
    Student student = new Student();
    student.setId("9999");
    student.setName("名無し");

    sut.updateStudent(student);
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

  @Test
  void 存在しないコースIDを指定しても例外は発生しないこと() {
    StudentCourse course = new StudentCourse();
    course.setId("9999");
    course.setStudentId("1");
    course.setCourseName("子育てセミナー");

    sut.updateStudentCourse(course);
  }
}