package raisetech.student.management02.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management02.data.CourseStatus;
import raisetech.student.management02.data.Student;
import raisetech.student.management02.data.StudentCourse;
import raisetech.student.management02.domain.StudentCourseDetail;
import raisetech.student.management02.domain.StudentDetail;

/**
 * 受講生詳細を受講生コース情報、もしくはその逆の変換を行うコンバーターです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報をマッピングする。
   * 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる。
   *
   * @param studentList        受講生一覧
   * @param studentCourseList  受講生コース情報のリスト
   * @param courseStatusList   申込状況リスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(
      List<Student> studentList,
      List<StudentCourse> studentCourseList,
      List<CourseStatus> courseStatusList) {

    List<StudentDetail> studentDetails = new ArrayList<>();

    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> filteredCourseList = studentCourseList.stream()
          .filter(course -> student.getId() == course.getStudentId()) // int同士で比較
          .collect(Collectors.toList());


      // 各コースに対してCourseStatusを紐づけてStudentCourseDetailを作成
      List<StudentCourseDetail> courseDetailList = filteredCourseList.stream()
          .map(course -> {
            int courseId = course.getId(); // int型
            CourseStatus status = courseStatusList.stream()
                .filter(s -> s.getStudentCourseId() == courseId)
                .findFirst()
                .orElse(null);

            return new StudentCourseDetail(course, status);
          })
          .collect(Collectors.toList());

      studentDetail.setStudentCourseDetailList(courseDetailList);

      studentDetails.add(studentDetail);
    });

    return studentDetails;
  }
}
