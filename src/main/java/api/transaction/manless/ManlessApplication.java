package api.transaction.manless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class ManlessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManlessApplication.class, args);
		System.out.println("MANLESS APPLICATION RUNNING............:"+ LocalDateTime.now());
	}

}
