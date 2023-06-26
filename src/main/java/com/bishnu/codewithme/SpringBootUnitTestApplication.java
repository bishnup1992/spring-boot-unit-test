package com.bishnu.codewithme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootUnitTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootUnitTestApplication.class, args);
	}

	@GetMapping("/hello")
	public String sayHello(){
		return "Hello to First Spring Spring Boot";
	}

}
