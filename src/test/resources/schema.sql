CREATE TABLE students (
  id INT AUTO_INCREMENT NOT NULL ,
  name varchar(255) NOT NULL,
  kana_name varchar(255),
  nick_name varchar(255),
  email varchar(255),
  area varchar(50),
  age int,
  sex varchar(10),
  remark text,
  isDeleted boolean DEFAULT FALSE,
  PRIMARY KEY (id)
);

CREATE TABLE students_courses (
  id int AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  course_name varchar(255),
  course_start_at datetime,
  course_end_at datetime,
  CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);
