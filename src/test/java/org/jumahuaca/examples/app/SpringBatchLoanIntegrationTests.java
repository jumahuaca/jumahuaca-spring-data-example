package org.jumahuaca.examples.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.jumahuaca.examples.config.Configuration;
import org.jumahuaca.examples.entity.UVAExchange;
import org.jumahuaca.examples.entity.UVALoan;
import org.jumahuaca.examples.entity.UVALoanFee;
import org.jumahuaca.examples.entity.UVALoanFeeId;
import org.jumahuaca.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
//@Transactional(propagation=Propagation.SUPPORTS)
@Transactional(propagation = Propagation.NEVER)
@ComponentScan({ "org.jumahuaca.examples.batch"})
@Import({SpringUvaApiApplication.class,Configuration.class,SpringBatchTestConfiguration.class})
public class SpringBatchLoanIntegrationTests {
	
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
	
	@Autowired
	private TestEntityManager entityManager;
	
	 @Autowired
	 private JobLauncherTestUtils jobLauncherTestUtils;
	 
	@BeforeEach
	//@Transactional
	public void setup() throws Exception {
		entityManager.getEntityManager().getTransaction().begin();
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
		UVALoanFeeId id2 = new UVALoanFeeId();
		id2.setFeeNumber(FEE_NUMBER_2);
		id2.setLoanId(LOAN_ID);
		UVALoanFee fee2 = new UVALoanFee();
		fee2.setId(id2);
		fee2.setFeeDate(LocalDate.of(FEE_2_YEAR, FEE_2_MONTH, FEE_2_DAY));
		fee2.setInitialCapital(FEE_2_INITIAL_CAPITAL);
		fee2.setInitialInterest(FEE_2_INITIAL_INTEREST);
		fee2.setInitialTotal(FEE_2_INITIAL_TOTAL);
		UVALoanFeeId id3 = new UVALoanFeeId();
		id3.setFeeNumber(FEE_NUMBER_3);
		id3.setLoanId(LOAN_ID);
		UVALoanFee fee3 = new UVALoanFee();
		fee3.setId(id3);
		fee3.setFeeDate(LocalDate.of(FEE_3_YEAR, FEE_3_MONTH, FEE_3_DAY));
		fee3.setInitialCapital(FEE_3_INITIAL_CAPITAL);
		fee3.setInitialInterest(FEE_3_INITIAL_INTEREST);
		fee3.setInitialTotal(FEE_3_INITIAL_TOTAL);
		UVAExchange exchange0 = new UVAExchange(LocalDate.of(FEE_0_EXCHANGE_YEAR, FEE_0_EXCHANGE_MONTH, FEE_0_EXCHANGE_DAY), FEE_0_EXCHANGE_RATE);
		UVAExchange exchange1 = new UVAExchange(LocalDate.of(FEE_1_EXCHANGE_YEAR, FEE_1_EXCHANGE_MONTH, FEE_1_EXCHANGE_DAY), FEE_1_EXCHANGE_RATE);
		UVAExchange exchange2 = new UVAExchange(LocalDate.of(FEE_2_EXCHANGE_YEAR, FEE_2_EXCHANGE_MONTH, FEE_2_EXCHANGE_DAY), FEE_2_EXCHANGE_RATE);
		UVAExchange exchange3 = new UVAExchange(LocalDate.of(FEE_3_EXCHANGE_YEAR, FEE_3_EXCHANGE_MONTH, FEE_3_EXCHANGE_DAY), FEE_3_EXCHANGE_RATE);

		entityManager.persist(loan);
		entityManager.persist(fee1);
		entityManager.persist(fee2);
		entityManager.persist(fee3);
		entityManager.persist(exchange0);
		entityManager.persist(exchange1);
		entityManager.persist(exchange2);
		entityManager.persist(exchange3);
		entityManager.getEntityManager().getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	@IntegrationTest
	//@Transactional(propagation=Propagation.REQUIRES_NEW)
	//@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void testRunJobShouldCompleteOk() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addLong("loanId", Long.valueOf(LOAN_ID))
				.addDate("date", new Date()).toJobParameters();
		//jobLauncherTestUtils.setJobLauncher(new AsyncJobLauncher(jobLauncherTestUtils.getJobLauncher()));
		jobLauncherTestUtils.launchJob(jobParameters);
		List<UVALoanFee> fees = (List<UVALoanFee>) entityManager.getEntityManager()
				.createQuery("from UVALoanFee f where f.id.loanId = :loanId").setParameter("loanId", LOAN_ID)
				.getResultList();
		
		assertNotNull(fees);
		assertThat(fees.get(0).getFinalCapital()).isEqualTo(FEE_1_FINAL_CAPITAL);
		assertThat(fees.get(0).getFinalInterest()).isEqualTo(FEE_1_FINAL_INTEREST);
		assertThat(fees.get(0).getFinalTotal()).isEqualTo(FEE_1_FINAL_TOTAL);
		assertThat(fees.get(1).getFinalCapital()).isEqualTo(FEE_2_FINAL_CAPITAL);
		assertThat(fees.get(1).getFinalInterest()).isEqualTo(FEE_2_FINAL_INTEREST);
		assertThat(fees.get(1).getFinalTotal()).isEqualTo(FEE_2_FINAL_TOTAL);
		assertThat(fees.get(2).getFinalCapital()).isEqualTo(FEE_3_FINAL_CAPITAL);
		assertThat(fees.get(2).getFinalInterest()).isEqualTo(FEE_3_FINAL_INTEREST);
		assertThat(fees.get(2).getFinalTotal()).isEqualTo(FEE_3_FINAL_TOTAL);
	}
	
}
