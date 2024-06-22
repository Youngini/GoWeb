package Goweb.FormMaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FormMakerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(FormMakerApplication.class)
				.properties("spring.config.location=classpath:/application.yml,classpath:/application-secret.yml")
				.run(args);

	}
}
