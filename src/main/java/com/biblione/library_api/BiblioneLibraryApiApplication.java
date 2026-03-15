package com.biblione.library_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BiblioneLibraryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiblioneLibraryApiApplication.class, args);
	}

}
