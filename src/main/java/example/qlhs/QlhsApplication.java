package example.qlhs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class QlhsApplication {

	public static void main(String[] args) {
		SpringApplication.run(QlhsApplication.class, args);
		System.out.println("=================== Backend Application ===================");
		System.out.println();
		System.out.println("\t\t\thttp://localhost:8080");
		System.out.println();
		System.out.println("===========================================================");
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
