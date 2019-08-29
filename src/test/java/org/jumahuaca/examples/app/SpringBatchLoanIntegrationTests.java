package org.jumahuaca.examples.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.transaction.Transactional;

import org.jumahuaca.examples.batch.UVALoanFeeUpdateJob;
import org.jumahuaca.examples.batch.UVALoanFeeUpdateProcessor;
import org.jumahuaca.examples.batch.UVALoanFeeUpdateReader;
import org.jumahuaca.examples.batch.UVALoanFeeUpdateWriter;
import org.jumahuaca.examples.config.Configuration;
import org.jumahuaca.examples.entity.UVALoan;
import org.jumahuaca.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@SpringBatchTest
//@ContextConfiguration(classes=SpringBatchTestConfiguration.class)
@Import({SpringUvaApiApplication.class,Configuration.class,UVALoanFeeUpdateJob.class, UVALoanFeeUpdateReader.class, UVALoanFeeUpdateProcessor.class, UVALoanFeeUpdateWriter.class})
public class SpringBatchLoanIntegrationTests {
	
	private static final Long COHOLDER_DNI = 31111111L;
	
	private static final Long HOLDER_DNI = 21111111L;

	private static final int LOAN_DATE_YEAR = 2017;

	private static final int LOAN_DATE_MONTH = 9;

	private static final int LOAN_DATE_DAY = 20;

	private static final BigDecimal PESOS_VALUE = BigDecimal.valueOf(1132800.0);
	
	private static final BigDecimal UVA_VALUE = BigDecimal.valueOf(56470.59);
	
	@Autowired
	private TestEntityManager entityManager;
	
	 @Autowired
	 private JobLauncherTestUtils jobLauncherTestUtils;
	
	@Transactional
	@BeforeEach
	public void setup() throws Exception {
		UVALoan loan = new UVALoan();
		loan.setCoholderDNI(COHOLDER_DNI);
		loan.setHolderDNI(HOLDER_DNI);
		loan.setLoanDate(LocalDate.of(LOAN_DATE_YEAR, LOAN_DATE_MONTH, LOAN_DATE_DAY));
		loan.setPesosValue(PESOS_VALUE);
		loan.setUvaValue(UVA_VALUE);
		
		entityManager.persist(loan);
		
//		UVAExchange exchange1 = new UVAExchange(LocalDate.of(TEST_YEAR_1, TEST_MONTH_1, TEST_DAY_1), TEST_RATE_1);
//		UVAExchange exchange2 = new UVAExchange(LocalDate.of(TEST_YEAR_2, TEST_MONTH_2, TEST_DAY_2), TEST_RATE_2);
//		entityManager.persist(exchange1);
//		entityManager.persist(exchange2);
//		entityManager.flush();
	}
	
	@IntegrationTest
	public void testRunJobShouldCompleteOk() {
		assertNotNull(jobLauncherTestUtils);
		
	}
	
}
