package raisetech.student.management02.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private Integer id;
  private String name;
  private String kanaName;
  private String nickname;
  private String email;
  private String area;
  private int age;
  private String sex;
  //課題で追加
  private String remark;
  private boolean isDeleted;
}
