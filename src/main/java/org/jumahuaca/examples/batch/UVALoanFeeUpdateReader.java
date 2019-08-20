package org.jumahuaca.examples.batch;

import java.util.List;

import org.jumahuaca.examples.entity.UVALoanFee;
import org.jumahuaca.examples.repository.UVALoanFeeRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class UVALoanFeeUpdateReader implements ItemReader<UVALoanFee> {
	
	@Autowired
	private UVALoanFeeRepository repository;
	
	private List<UVALoanFee> items;
	
	private Long loanId;

	@Override
	public UVALoanFee read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(items==null) {
			items = repository.findByLoanId(loanId.intValue());
		}
		if (!items.isEmpty()) {
			return items.remove(0);
		}
		return null;		
	}
	
	@Value("#{jobParameters['loanId']}")
	public void setLoanId(final Long loanId) {
		this.loanId = loanId;
	}
	
	

}
