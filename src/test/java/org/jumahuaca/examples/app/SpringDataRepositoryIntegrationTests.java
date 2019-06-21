package org.jumahuaca.examples.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.jumahuaca.examples.entity.UVAExchange;
import org.jumahuaca.examples.repository.UvaExchangeRepository;
import org.jumahuaca.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SpringDataRepositoryIntegrationTests {

	private static final int TEST_YEAR_1 = 2018;

	private static final Month TEST_MONTH_1 = Month.DECEMBER;

	private static final int TEST_DAY_1 = 15;

	private static final BigDecimal TEST_RATE_1 = BigDecimal.valueOf(30.60d);

	private static final int TEST_YEAR_2 = 2018;

	private static final Month TEST_MONTH_2 = Month.DECEMBER;

	private static final int TEST_DAY_2 = 14;

	private static final BigDecimal TEST_RATE_2 = BigDecimal.valueOf(30.55d);

	private static final int SIZE_SELECT_ALL = 2;

	private String testDay1 = "2018-12-15";

	private String testDay2 = "2018-12-14";

	private String testDay3 = "2018-12-18";

	private String testDayError1 = "2019-12-15";

	private Double testRate1 = 30.6d;

	private Double testRate2 = 30.55d;

	private Double testRate3 = 30.72d;

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UvaExchangeRepository repository;

	@BeforeEach
	public void setup() throws Exception {
		UVAExchange exchange1 = new UVAExchange(LocalDate.of(TEST_YEAR_1, TEST_MONTH_1, TEST_DAY_1), TEST_RATE_1);
		UVAExchange exchange2 = new UVAExchange(LocalDate.of(TEST_YEAR_2, TEST_MONTH_2, TEST_DAY_2), TEST_RATE_2);
		entityManager.persist(exchange1);
		entityManager.persist(exchange2);
		entityManager.flush();
	}

	@IntegrationTest
	public void testFindExchangeByDayShouldWork() throws SQLException {
		LocalDate param = LocalDate.parse(testDay1);
		Optional<UVAExchange> result = repository.findById(param);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getDate()).isEqualTo(param);
		assertThat(result.get().getRate()).isEqualTo(BigDecimal.valueOf(testRate1));
	}

	@IntegrationTest
	public void testFindExchangeByDayShouldNotFind() throws SQLException {
		LocalDate param = LocalDate.parse(testDayError1);
		Optional<UVAExchange> result = repository.findById(param);
		assertThat(result.isPresent()).isFalse();
	}

	@IntegrationTest
	public void testSearchAllShouldWork() throws SQLException {
		List<UVAExchange> result = (List<UVAExchange>) repository.findAll();
		assertThat(result.size()).isEqualTo(SIZE_SELECT_ALL);
		assertThat(result.get(0).getDate()).isEqualTo(testDay1);
		assertThat(result.get(0).getRate()).isEqualTo(BigDecimal.valueOf(testRate1));
		assertThat(result.get(1).getDate()).isEqualTo(testDay2);
		assertThat(result.get(1).getRate()).isEqualTo(BigDecimal.valueOf(testRate2));
	}

	@IntegrationTest
	public void testCreateShouldWork() throws SQLException {
		UVAExchange toCreate = repository
				.save(new UVAExchange(LocalDate.parse(testDay3), BigDecimal.valueOf(testRate3)));
		assertThat(toCreate).isNotNull();
		assertThat(toCreate.getDate()).isEqualTo(testDay3);
		assertThat(toCreate.getRate()).isEqualTo(BigDecimal.valueOf(testRate3));
	}

	@IntegrationTest
	public void testUpdateShouldWork() throws SQLException {
		UVAExchange toCreate = repository
				.save(new UVAExchange(LocalDate.parse(testDay1), BigDecimal.valueOf(testRate1)));
		assertThat(toCreate).isNotNull();
		assertThat(toCreate.getDate()).isEqualTo(testDay1);
		assertThat(toCreate.getRate()).isEqualTo(BigDecimal.valueOf(testRate1));
	}

	@IntegrationTest
	public void testDeleteShouldWork() throws SQLException {
		repository.delete(new UVAExchange(LocalDate.parse(testDay1), BigDecimal.valueOf(testRate1)));
	}

}
