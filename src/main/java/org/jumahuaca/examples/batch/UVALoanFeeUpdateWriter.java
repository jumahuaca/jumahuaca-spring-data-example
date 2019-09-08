package org.jumahuaca.examples.batch;

import java.util.List;

import javax.transaction.Transactional;

import org.jumahuaca.examples.entity.UVALoanFee;
import org.jumahuaca.examples.repository.UVALoanFeeRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UVALoanFeeUpdateWriter implements ItemWriter<UVALoanFee>{
	
	@Autowired
	private UVALoanFeeRepository repository;

	@Override
	@Transactional
	public void write(List<? extends UVALoanFee> items) throws Exception {
		for (UVALoanFee uvaLoanFee : items) {
			repository.save(uvaLoanFee);
		}
	}

	public void setRepository(UVALoanFeeRepository repository) {
		this.repository = repository;
	}
	
	

}
