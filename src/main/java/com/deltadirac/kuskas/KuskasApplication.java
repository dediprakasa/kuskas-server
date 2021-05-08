package com.deltadirac.kuskas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KuskasApplication {

	public static void main(String[] args) {
		SpringApplication.run(KuskasApplication.class, args);
	}

}
