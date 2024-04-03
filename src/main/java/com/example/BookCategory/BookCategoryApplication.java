package com.example.BookCategory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication

public class BookCategoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookCategoryApplication.class, args);
	}

}
