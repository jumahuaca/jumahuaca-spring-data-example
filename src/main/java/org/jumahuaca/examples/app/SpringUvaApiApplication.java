package org.jumahuaca.examples.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = { "org.jumahuaca" })
@EntityScan(basePackages={"org.jumahuaca.examples.entity"})
@EnableJpaRepositories(basePackages={"org.jumahuaca.examples.repository"})
@EnableAsync
public class SpringUvaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringUvaApiApplication.class, args);
	}

}
