package org.jumahuaca.examples.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
@org.springframework.context.annotation.Configuration

@EnableJpaRepositories(basePackages={"org.jumahuaca.examples.repository"})
@EnableAsync
@EntityScan(basePackages={"org.jumahuaca.examples.entity"})
public class Configuration {

}
