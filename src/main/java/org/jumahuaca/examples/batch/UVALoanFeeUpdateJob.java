package org.jumahuaca.examples.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jumahuaca.examples.entity.UVALoanFee;
import org.jumahuaca.examples.repository.UVALoanFeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

@Configuration
public class UVALoanFeeUpdateJob {
	
//	@Autowired
//	private UVALoanFeeRepository repository;
	
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
	
//	@Bean
//	@StepScope
//	public RepositoryItemReader<UVALoanFee> reader(@Value("#{jobParameters['loanId']}") Long
//			loanId){
//		List<Object> args = new ArrayList<Object>();
//		args.add(loanId.intValue());
//		//args.add(PageRequest.of(0, 360));
//		Map<String, Direction> sort = new HashMap<String, Direction>();
//	    sort.put("id", Direction.ASC);
//		return new RepositoryItemReaderBuilder<UVALoanFee>().
//		repository(repository).methodName("findByLoanId").arguments(args).sorts(sort).saveState(false).build();		    
//	}


}
