package raisetech.student.management02.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import raisetech.student.management02.data.Student;
import raisetech.student.management02.domain.StudentDetail;
import raisetech.student.management02.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void 一覧検索が成功すること() throws Exception {
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void ID検索が成功すること() throws Exception {
    String id = "123";

    Student student = new Student();
    student.setId(id);
    student.setName("青木文太");
    student.setKanaName("アオキブンタ");
    student.setNickname("ブンチン");
    student.setEmail("aoki.bunta@example.com");
    student.setArea("大阪");
    student.setSex("男性");

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(new ArrayList<>());

    when(service.searchStudent(id)).thenReturn(detail);

    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student.id").value("123"))
        .andExpect(jsonPath("$.student.name").value("青木文太"))
        .andExpect(jsonPath("$.student.kanaName").value("アオキブンタ"))
        .andExpect(jsonPath("$.student.nickname").value("ブンチン"))
        .andExpect(jsonPath("$.student.email").value("aoki.bunta@example.com"))
        .andExpect(jsonPath("$.student.area").value("大阪"))
        .andExpect(jsonPath("$.student.sex").value("男性"))
        .andExpect(jsonPath("$.studentCourseList").isArray())
        .andExpect(jsonPath("$.studentCourseList").isEmpty());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 登録が成功すること() throws Exception {
    Student student = new Student();
    student.setId("321");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickname("タロちゃん");
    student.setEmail("yamada@example.com");
    student.setArea("東京");
    student.setSex("男性");

    StudentDetail inputDetail = new StudentDetail();
    inputDetail.setStudent(student);
    inputDetail.setStudentCourseList(new ArrayList<>());

    when(service.registerStudent(Mockito.any(StudentDetail.class))).thenReturn(inputDetail);

    String json = objectMapper.writeValueAsString(inputDetail);

    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student.id").value("321"))
        .andExpect(jsonPath("$.student.name").value("山田太郎"));

    Mockito.verify(service, times(1)).registerStudent(Mockito.any(StudentDetail.class));
  }

  @Test
  void 更新が成功すること() throws Exception {
    Student student = new Student();
    student.setId("321");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickname("タロちゃん");
    student.setEmail("yamada@example.com");
    student.setArea("東京");
    student.setSex("男性");

    StudentDetail updateDetail = new StudentDetail();
    updateDetail.setStudent(student);
    updateDetail.setStudentCourseList(new ArrayList<>());

    doNothing().when(service).updateStudent(Mockito.any(StudentDetail.class));

    String json = objectMapper.writeValueAsString(updateDetail);

    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk())
        .andExpect(content().string("更新が完了しました"));

    Mockito.verify(service, times(1)).updateStudent(Mockito.any(StudentDetail.class));
  }
}
