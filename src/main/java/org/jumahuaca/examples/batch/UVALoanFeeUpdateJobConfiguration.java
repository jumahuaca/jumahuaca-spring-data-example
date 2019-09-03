package org.jumahuaca.examples.batch;

import org.jumahuaca.examples.entity.UVALoanFee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UVALoanFeeUpdateJobConfiguration {
	
	@Autowired
	private UVALoanFeeUpdateReader reader;
	
	@Autowired
	private UVALoanFeeUpdateProcessor processor;
	
	@Autowired
	private UVALoanFeeUpdateWriter writer;
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;


	@Bean
	public Step step() {
		return stepBuilderFactory.get("step")
				.<UVALoanFee,UVALoanFee>chunk(1)
				.reader(reader)
				.writer(writer)
				.processor(processor)
				.build();
	}

	@Bean(name="feeUpdateJob")
	public Job job() {
		return jobBuilderFactory.get("feeUpdateJob")
				.start(step())
				.build();
	}
	


}
