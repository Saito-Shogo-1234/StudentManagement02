package raisetech.student.management02.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management02.controller.converter.StudentConverter;
import raisetech.student.management02.data.Student;
import raisetech.student.management02.data.StudentCourse;
import raisetech.student.management02.domain.StudentDetail;
import raisetech.student.management02.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索＿リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生詳細検索＿IDに紐づきリポジトリの処理が適切に呼び出されていること() {
    String id = "123";
    Student student = new Student();
    student.setId(Integer.valueOf("123"));
    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourseList);

    sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(student.getId());
  }

  @Test
  void 受講生登録処理＿リポジトリの処理が適切に呼び出せていること() {
    Student student = new Student();
    student.setId(Integer.valueOf("123"));
    StudentCourse course = new StudentCourse();
    List<StudentCourse> courseList = List.of(course);
    StudentDetail studentDetail = new StudentDetail(student, courseList);

    sut.registerStudent(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(course);
  }

  @Test
  void 受講生更新処理＿リポジトリの更新メソッドが適切に呼び出されていること() {
    Student student = new Student();
    student.setId(Integer.valueOf("123"));
    StudentCourse course = new StudentCourse();
    List<StudentCourse> courseList = List.of(course);
    StudentDetail studentDetail = new StudentDetail(student, courseList);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(course);
  }

  @Test
  void 受講生コース初期化＿正しく値が設定されていること() {
    Student student = new Student();
    student.setId(Integer.valueOf("123"));

    StudentCourse studentCourse = new StudentCourse();

    LocalDateTime before = LocalDateTime.now();

    sut.initStudentsCourse(studentCourse, student);

    LocalDateTime after = LocalDateTime.now();

    assertEquals(123, studentCourse.getStudentId());
    assertTrue(!studentCourse.getCourseStartAt().isBefore(before)
        && !studentCourse.getCourseStartAt().isAfter(after));
    assertEquals(studentCourse.getCourseStartAt().plusYears(1), studentCourse.getCourseEndAt());
  }

}