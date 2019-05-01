package org.jumahuaca.examples.springdata.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "org.jumahuaca" })
@EntityScan(basePackages={"org.jumahuaca.examples.springdata.entity"})
@EnableJpaRepositories(basePackages={"org.jumahuaca.examples.springdata.repository"})
public class SpringDataUvaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataUvaApiApplication.class, args);
	}

}
