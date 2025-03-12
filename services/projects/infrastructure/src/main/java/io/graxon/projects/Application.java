package io.graxon.projects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication(scanBasePackages = "io.graxon.projects")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
