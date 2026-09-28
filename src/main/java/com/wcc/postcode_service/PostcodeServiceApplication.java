package com.wcc.postcode_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PostcodeServiceApplication {

	// This is the entry point of the application.
	// Maven starts java application
	public static void main(String[] args) {
		// java starts spring boot application
		// spring boot starts embedded tomcat server and runs the application
		// when spring boot starts, it scans for all the classes annotated with @RestController and registers them as REST controllers. That's why need to restart when added a new controller class.
		SpringApplication.run(PostcodeServiceApplication.class, args);
	}

}
