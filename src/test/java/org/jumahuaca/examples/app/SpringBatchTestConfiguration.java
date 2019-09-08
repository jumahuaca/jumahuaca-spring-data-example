package org.jumahuaca.examples.app;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableBatchProcessing
public class SpringBatchTestConfiguration {
	
	@Autowired
    @Qualifier("feeUpdateJob")
    private Job feeUpdateJob;    

	@Bean
	public JobLauncherTestUtils utils() throws Exception {
		return new JobLauncherTestUtils() {
			@Override
			@Autowired
			public void setJob(@Qualifier("feeUpdateJob") Job job) {
				super.setJob(job);
			}

		};
	}

}
