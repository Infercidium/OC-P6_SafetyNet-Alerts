package com.infercidium.safetynet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SafetyNetApplication {

	/**
	 * The main API method used to launch the application.
	 * @param args the application main arguments.
	 */
	public static void main(final String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
	}
}
