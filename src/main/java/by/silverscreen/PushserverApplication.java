package by.silverscreen;

import by.silverscreen.config.JpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories("by.silverscreen.DAO")
public class PushserverApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(new Class<?>[] {PushserverApplication.class, JpaConfig.class}, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<PushserverApplication> applicationClass = PushserverApplication.class;
}
