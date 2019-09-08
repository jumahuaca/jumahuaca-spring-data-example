package org.jumahuaca.examples.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jumahuaca.examples.batch.UVALoanFeeUpdateProcessor;
import org.jumahuaca.examples.batch.UVALoanFeeUpdateReader;
import org.jumahuaca.examples.batch.UVALoanFeeUpdateWriter;
import org.jumahuaca.examples.entity.UVAExchange;
import org.jumahuaca.examples.entity.UVALoan;
import org.jumahuaca.examples.entity.UVALoanFee;
import org.jumahuaca.examples.entity.UVALoanFeeId;
import org.jumahuaca.examples.repository.UVALoanFeeRepository;
import org.jumahuaca.examples.repository.UvaExchangeRepository;
import org.jumahuaca.extensions.SpringBatchChunkExtension;
import org.jumahuaca.extensions.SpringBatchChunkTestDoubleHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class SpringBatchChunkUpdateFeesTests implements SpringBatchChunkTestDoubleHelper<UVALoanFee> {

	private static final Long COHOLDER_DNI = 31111111L;

	private static final Long HOLDER_DNI = 21111111L;

	private static final int LOAN_DATE_YEAR = 2017;

	private static final int LOAN_DATE_MONTH = 9;

	private static final int LOAN_DATE_DAY = 20;

	private static final BigDecimal PESOS_VALUE = BigDecimal.valueOf(1132800.0);

	private static final BigDecimal UVA_VALUE = BigDecimal.valueOf(56470.59);

	private static final Integer FEE_NUMBER_1 = 1;

	private static final Integer LOAN_ID = 1;

	private static final int FEE_1_YEAR = 2017;

	private static final int FEE_1_MONTH = 11;

	private static final int FEE_1_DAY = 10;

	private static final BigDecimal FEE_1_INITIAL_CAPITAL = BigDecimal.valueOf(3265.78);

	private static final BigDecimal FEE_1_INITIAL_INTEREST = BigDecimal.valueOf(5539.86);

	private static final BigDecimal FEE_1_INITIAL_TOTAL = BigDecimal.valueOf(8805.64);

	private static final BigDecimal FEE_1_FINAL_CAPITAL = BigDecimal.valueOf(3356.948335);

	private static final BigDecimal FEE_1_FINAL_TOTAL = BigDecimal.valueOf(5694.512124);

	private static final BigDecimal FEE_1_FINAL_INTEREST = BigDecimal.valueOf(9051.460459);

	private static final BigDecimal FEE_2_FINAL_CAPITAL = BigDecimal.valueOf(3417.36);

	private static final BigDecimal FEE_2_FINAL_INTEREST = BigDecimal.valueOf(3437.35);

	private static final BigDecimal FEE_2_FINAL_TOTAL = BigDecimal.valueOf(6854.71);

	private static final BigDecimal FEE_3_FINAL_CAPITAL = BigDecimal.valueOf(3481.36);

	private static final BigDecimal FEE_3_FINAL_INTEREST = BigDecimal.valueOf(3481.43);

	private static final BigDecimal FEE_3_FINAL_TOTAL = BigDecimal.valueOf(6962.79);

	private static final Integer FEE_NUMBER_2 = 2;

	private static final int FEE_2_YEAR = 2017;

	private static final int FEE_2_MONTH = 12;

	private static final int FEE_2_DAY = 11;

	private static final BigDecimal FEE_2_INITIAL_CAPITAL = BigDecimal.valueOf(3275.31);

	private static final BigDecimal FEE_2_INITIAL_INTEREST = BigDecimal.valueOf(3294.47);

	private static final BigDecimal FEE_2_INITIAL_TOTAL = BigDecimal.valueOf(6569.78);

	private static final Integer FEE_NUMBER_3 = 3;

	private static final int FEE_3_YEAR = 2018;

	private static final int FEE_3_MONTH = 1;

	private static final int FEE_3_DAY = 10;

	private static final BigDecimal FEE_3_INITIAL_CAPITAL = BigDecimal.valueOf(3284.86);

	private static final BigDecimal FEE_3_INITIAL_INTEREST = BigDecimal.valueOf(3284.92);

	private static final BigDecimal FEE_3_INITIAL_TOTAL = BigDecimal.valueOf(6569.78);

	private static final int FEE_0_EXCHANGE_YEAR = 2017;

	private static final int FEE_0_EXCHANGE_MONTH = 9;

	private static final int FEE_0_EXCHANGE_DAY = 20;

	private static final BigDecimal FEE_0_EXCHANGE_RATE = BigDecimal.valueOf(20.06);

	private static final int FEE_1_EXCHANGE_YEAR = 2017;

	private static final int FEE_1_EXCHANGE_MONTH = 11;

	private static final int FEE_1_EXCHANGE_DAY = 10;

	private static final BigDecimal FEE_1_EXCHANGE_RATE = BigDecimal.valueOf(20.62);

	private static final int FEE_2_EXCHANGE_YEAR = 2017;

	private static final int FEE_2_EXCHANGE_MONTH = 12;

	private static final int FEE_2_EXCHANGE_DAY = 11;

	private static final BigDecimal FEE_2_EXCHANGE_RATE = BigDecimal.valueOf(20.93);

	private static final int FEE_3_EXCHANGE_YEAR = 2018;

	private static final int FEE_3_EXCHANGE_MONTH = 01;

	private static final int FEE_3_EXCHANGE_DAY = 10;

	private static final BigDecimal FEE_3_EXCHANGE_RATE = BigDecimal.valueOf(21.26);
	@RegisterExtension
	public final SpringBatchChunkExtension<UVALoanFee, UVALoanFee> extension = new SpringBatchChunkExtension<UVALoanFee, UVALoanFee>();

	@MockBean
	private UVALoanFeeRepository feeRepository;

	@MockBean
	private UvaExchangeRepository uvaExchangeRepository;

	private UVALoanFeeUpdateReader reader;

	private UVALoanFeeUpdateProcessor processor;

	private UVALoanFeeUpdateWriter writer;

	@BeforeEach
	public void setup() {
		reader = new UVALoanFeeUpdateReader();
		processor = new UVALoanFeeUpdateProcessor();
		writer = new UVALoanFeeUpdateWriter();

	}

	@Test
	public void testChunkJobShouldWork()
			throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {

		List<UVALoanFee> written = extension.simulateSimpleRun(reader, processor, writer, this);
		List<UVALoanFee> expected = buildToBeWrittenFakeResult();
		assertThat(written).isEqualTo(expected);

	}

	@Override
	public void mockInjectionsReadOk() {
		UVALoan loan = new UVALoan();
		loan.setCoholderDNI(COHOLDER_DNI);
		loan.setHolderDNI(HOLDER_DNI);
		loan.setLoanDate(LocalDate.of(LOAN_DATE_YEAR, LOAN_DATE_MONTH, LOAN_DATE_DAY));
		loan.setPesosValue(PESOS_VALUE);
		loan.setUvaValue(UVA_VALUE);
		UVALoanFeeId id1 = new UVALoanFeeId();
		id1.setFeeNumber(FEE_NUMBER_1);
		id1.setLoanId(LOAN_ID);
		UVALoanFee fee1 = new UVALoanFee();
		fee1.setId(id1);
		fee1.setFeeDate(LocalDate.of(FEE_1_YEAR, FEE_1_MONTH, FEE_1_DAY));
		fee1.setInitialCapital(FEE_1_INITIAL_CAPITAL);
		fee1.setInitialInterest(FEE_1_INITIAL_INTEREST);
		fee1.setInitialTotal(FEE_1_INITIAL_TOTAL);
		fee1.setFinalCapital(FEE_1_FINAL_CAPITAL);
		fee1.setFinalInterest(FEE_1_FINAL_INTEREST);
		fee1.setFinalTotal(FEE_1_FINAL_TOTAL);
		fee1.setLoan(loan);
		fee1.setLoanId(loan.getId());
		UVALoanFeeId id2 = new UVALoanFeeId();
		id2.setFeeNumber(FEE_NUMBER_2);
		id2.setLoanId(LOAN_ID);
		UVALoanFee fee2 = new UVALoanFee();
		fee2.setId(id2);
		fee2.setFeeDate(LocalDate.of(FEE_2_YEAR, FEE_2_MONTH, FEE_2_DAY));
		fee2.setInitialCapital(FEE_2_INITIAL_CAPITAL);
		fee2.setInitialInterest(FEE_2_INITIAL_INTEREST);
		fee2.setInitialTotal(FEE_2_INITIAL_TOTAL);
		fee2.setLoan(loan);
		fee2.setLoanId(loan.getId());

		UVALoanFeeId id3 = new UVALoanFeeId();
		id3.setFeeNumber(FEE_NUMBER_3);
		id3.setLoanId(LOAN_ID);
		UVALoanFee fee3 = new UVALoanFee();
		fee3.setId(id3);
		fee3.setFeeDate(LocalDate.of(FEE_3_YEAR, FEE_3_MONTH, FEE_3_DAY));
		fee3.setInitialCapital(FEE_3_INITIAL_CAPITAL);
		fee3.setInitialInterest(FEE_3_INITIAL_INTEREST);
		fee3.setInitialTotal(FEE_3_INITIAL_TOTAL);
		fee3.setLoan(loan);
		fee3.setLoanId(loan.getId());

		List<UVALoanFee> toReturn = new ArrayList<UVALoanFee>();
		toReturn.add(fee1);
		toReturn.add(fee2);
		toReturn.add(fee3);
		when(feeRepository.findByLoanId(LOAN_ID)).thenReturn(toReturn);
		reader.setRepository(feeRepository);
		reader.setLoanId(Long.valueOf(LOAN_ID));

	}

	@Override
	public void mockInjectionsProcessOk() {
		UVAExchange exchange0 = new UVAExchange(
				LocalDate.of(FEE_0_EXCHANGE_YEAR, FEE_0_EXCHANGE_MONTH, FEE_0_EXCHANGE_DAY), FEE_0_EXCHANGE_RATE);
		UVAExchange exchange1 = new UVAExchange(
				LocalDate.of(FEE_1_EXCHANGE_YEAR, FEE_1_EXCHANGE_MONTH, FEE_1_EXCHANGE_DAY), FEE_1_EXCHANGE_RATE);
		UVAExchange exchange2 = new UVAExchange(
				LocalDate.of(FEE_2_EXCHANGE_YEAR, FEE_2_EXCHANGE_MONTH, FEE_2_EXCHANGE_DAY), FEE_2_EXCHANGE_RATE);
		UVAExchange exchange3 = new UVAExchange(
				LocalDate.of(FEE_3_EXCHANGE_YEAR, FEE_3_EXCHANGE_MONTH, FEE_3_EXCHANGE_DAY), FEE_3_EXCHANGE_RATE);
		Optional<UVAExchange> optional0 = Optional.of(exchange0);
		Optional<UVAExchange> optional1 = Optional.of(exchange1);
		Optional<UVAExchange> optional2 = Optional.of(exchange2);
		Optional<UVAExchange> optional3 = Optional.of(exchange3);

		when(uvaExchangeRepository
				.findById(LocalDate.of(FEE_0_EXCHANGE_YEAR, FEE_0_EXCHANGE_MONTH, FEE_0_EXCHANGE_DAY)))
						.thenReturn(optional0);
		when(uvaExchangeRepository
				.findById(LocalDate.of(FEE_1_EXCHANGE_YEAR, FEE_1_EXCHANGE_MONTH, FEE_1_EXCHANGE_DAY)))
						.thenReturn(optional1);
		when(uvaExchangeRepository
				.findById(LocalDate.of(FEE_2_EXCHANGE_YEAR, FEE_2_EXCHANGE_MONTH, FEE_2_EXCHANGE_DAY)))
						.thenReturn(optional2);
		when(uvaExchangeRepository
				.findById(LocalDate.of(FEE_3_EXCHANGE_YEAR, FEE_3_EXCHANGE_MONTH, FEE_3_EXCHANGE_DAY)))
						.thenReturn(optional3);

		processor.setUvaExchangeRepository(uvaExchangeRepository);

	}

	public List<UVALoanFee> buildToBeWrittenFakeResult() {
		UVALoan loan = new UVALoan();
		loan.setCoholderDNI(COHOLDER_DNI);
		loan.setHolderDNI(HOLDER_DNI);
		loan.setLoanDate(LocalDate.of(LOAN_DATE_YEAR, LOAN_DATE_MONTH, LOAN_DATE_DAY));
		loan.setPesosValue(PESOS_VALUE);
		loan.setUvaValue(UVA_VALUE);
		UVALoanFeeId id2 = new UVALoanFeeId();
		id2.setFeeNumber(FEE_NUMBER_2);
		id2.setLoanId(LOAN_ID);
		UVALoanFee fee2 = new UVALoanFee();
		fee2.setId(id2);
		fee2.setFeeDate(LocalDate.of(FEE_2_YEAR, FEE_2_MONTH, FEE_2_DAY));
		fee2.setInitialCapital(FEE_2_INITIAL_CAPITAL);
		fee2.setInitialInterest(FEE_2_INITIAL_INTEREST);
		fee2.setInitialTotal(FEE_2_INITIAL_TOTAL);
		fee2.setLoan(loan);
		fee2.setFinalCapital(FEE_2_FINAL_CAPITAL);
		fee2.setFinalInterest(FEE_2_FINAL_INTEREST);
		fee2.setFinalTotal(FEE_2_FINAL_TOTAL);
		fee2.setLoanId(loan.getId());
		UVALoanFeeId id3 = new UVALoanFeeId();
		id3.setFeeNumber(FEE_NUMBER_3);
		id3.setLoanId(LOAN_ID);
		UVALoanFee fee3 = new UVALoanFee();
		fee3.setId(id3);
		fee3.setFeeDate(LocalDate.of(FEE_3_YEAR, FEE_3_MONTH, FEE_3_DAY));
		fee3.setInitialCapital(FEE_3_INITIAL_CAPITAL);
		fee3.setInitialInterest(FEE_3_INITIAL_INTEREST);
		fee3.setInitialTotal(FEE_3_INITIAL_TOTAL);
		fee3.setFinalCapital(FEE_3_FINAL_CAPITAL);
		fee3.setFinalInterest(FEE_3_FINAL_INTEREST);
		fee3.setFinalTotal(FEE_3_FINAL_TOTAL);
		fee3.setLoan(loan);
		fee3.setLoanId(loan.getId());
		List<UVALoanFee> result = new ArrayList<UVALoanFee>();
		result.add(fee2);
		result.add(fee3);
		return result;
	}

	@Override
	public void mockInjectionsWriteOk() {
		writer.setRepository(feeRepository);
	}
}
