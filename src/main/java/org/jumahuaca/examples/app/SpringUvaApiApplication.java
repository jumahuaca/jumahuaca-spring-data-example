package org.jumahuaca.examples.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "org.jumahuaca" })
public class SpringUvaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringUvaApiApplication.class, args);
	}

}
