package by.silverscreen;

import by.silverscreen.config.JpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PushserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class<?>[] {PushserverApplication.class, JpaConfig.class}, args);
	}
}
