package org.jumahuaca.examples.batch;

import java.util.List;

import org.jumahuaca.examples.entity.UVALoanFee;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class UVALoanFeeUpdateWriter implements ItemWriter<UVALoanFee>{

	@Override
	public void write(List<? extends UVALoanFee> items) throws Exception {
		// TODO Auto-generated method stub
		for (UVALoanFee uvaLoanFee : items) {
			System.out.println("Fee" + uvaLoanFee.getLoanId());
		}
	}

}
