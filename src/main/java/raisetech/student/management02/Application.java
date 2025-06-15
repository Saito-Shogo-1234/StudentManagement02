package raisetech.student.management02;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	private String name = "Saito Shogo";
	private String age = "18";

	private Map<String,String> students = new HashMap<>();
	{
		students.put("Saito Shogo", "18");
		students.put("Tanaka Ichiro", "21");
		students.put("Suzuki Hanako", "22");
		students.put("Kobayashi Taro", "20");
		students.put("Yamada Yuki", "19");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/studentInfo")
	public String getStudentInfo() {
		return name + " " + age + "歳";
	}

	@PostMapping("/studentInfo")
	public void setName(String name, String age) {
		this.name = name;
		this.age = age;
	}

	@PostMapping("/studentName")
	public void updateStudentName(String name) {
		this.name = name;
	}

	@GetMapping("studentInfo2")
	public Map<String, String> getStudents() {
		return students;
	}

	@PostMapping("studentInfo2")
	public void updateStudent(String name, String age) {
		students.put(name,age);
	}

}