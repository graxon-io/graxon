package io.graxon.node;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication(scanBasePackages = "io.graxon.node")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
