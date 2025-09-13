package raisetech.student.management02.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import raisetech.student.management02.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索ができて空のリストが返ってくること() throws Exception {
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索が実行できて空で返ってくること() throws Exception {
    String id = "123";
    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 登録が成功すること() throws Exception {

    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
            """
                {
                    "student": {
                        "name" : "三橋桃子",
                        "kanaName" : "ミハシモモコ",
                        "nickname" : "モモカン",
                        "email" : "mihashi.momoko@example.com",
                        "area" : "京都",
                        "age" : "19",
                        "sex" : "女性",
                        "remark" : "NULL"
                    },
                    "studentCourseList" : []
                }
                """
        ))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新が実行できて空で返ってくること() throws Exception {

    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                      "student": {
                          "id" : "S012",
                          "name" : "常盤史郎",
                          "kanaName" : "トキワシロウ",
                          "nickname" : "シロトキ",
                          "email" : "shiro.tokiwa@example.com",
                          "area" : "山形", 
                          "age" : "64",
                          "sex" : "男性",
                          "remark" : ""
                      },
                      "studentCourseList" : [
                          {
                              "id": "12",
                              "studentId": "S012",
                              "courseName": "Wordコース",
                              "courseStartAt": "2025-09-06T22:04:06",
                              "courseEndAt": "2026-09-06T22:04:06"
                    
                          }
                      ]
                    }
                    """
            ))
            .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }
}
