package org.jumahuaca.examples.batch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.jumahuaca.examples.entity.UVAExchange;
import org.jumahuaca.examples.entity.UVALoanFee;
import org.jumahuaca.examples.repository.UvaExchangeRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UVALoanFeeUpdateProcessor implements ItemProcessor<UVALoanFee,UVALoanFee>{
	
	private static final int ROUNDING_DECIMALS = 2;

	private static final int DECIMALS = 6;
	
	@Autowired
	private UvaExchangeRepository uvaExchangeRepository;

	@Override
	public UVALoanFee process(UVALoanFee item) throws Exception {
		if(item.getFinalCapital()!=null && item.getFinalInterest()!=null && item.getFinalTotal() !=null) {
			return null;
		}
		
		try {
			LocalDate initialLoanDate = item.getLoan().getLoanDate();
			LocalDate feeDate = item.getFeeDate();
			UVAExchange initialUva = uvaExchangeRepository.findById(initialLoanDate).get();
			UVAExchange finalUva = uvaExchangeRepository.findById(feeDate).get();
			BigDecimal capital = updateValue(item.getInitialCapital(),initialUva,finalUva);
			BigDecimal interest = updateValue(item.getInitialInterest(),initialUva,finalUva);
			item.setFinalCapital(capital.setScale(ROUNDING_DECIMALS,RoundingMode.HALF_UP));
			item.setFinalInterest(interest.setScale(ROUNDING_DECIMALS,RoundingMode.HALF_UP));
			item.setFinalTotal(capital.add(interest).setScale(ROUNDING_DECIMALS,RoundingMode.HALF_UP));
		} catch (Exception e) {
			return null;
		}

		
		return item;
	}

	private BigDecimal updateValue(BigDecimal value, UVAExchange initialUva, UVAExchange finalUva) {
		return value.divide(initialUva.getRate(),DECIMALS,RoundingMode.HALF_UP).multiply(finalUva.getRate());
	}

	public void setUvaExchangeRepository(UvaExchangeRepository uvaExchangeRepository) {
		this.uvaExchangeRepository = uvaExchangeRepository;
	}
	
	

}
