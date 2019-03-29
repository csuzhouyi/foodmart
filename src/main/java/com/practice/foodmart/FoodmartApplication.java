package com.practice.foodmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class FoodmartApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodmartApplication.class, args);
	}


}
