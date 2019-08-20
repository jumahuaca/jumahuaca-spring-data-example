package org.jumahuaca.examples.batch;

import org.jumahuaca.examples.entity.UVALoanFee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class UVALoanFeeUpdateProcessor implements ItemProcessor<UVALoanFee,UVALoanFee>{

	@Override
	public UVALoanFee process(UVALoanFee item) throws Exception {
		return item;
	}

}
