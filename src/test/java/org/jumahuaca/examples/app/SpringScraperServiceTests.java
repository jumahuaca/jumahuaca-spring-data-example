package org.jumahuaca.examples.app;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jumahuaca.examples.beans.MonthYear;
import org.jumahuaca.examples.entity.UVAExchange;
import org.jumahuaca.examples.entity.UVAScrapingProcess;
import org.jumahuaca.examples.repository.UVAScrapingProcessRepository;
import org.jumahuaca.examples.repository.UvaExchangeRepository;
import org.jumahuaca.examples.scraper.UVAScraper;
import org.jumahuaca.examples.service.UVAScrapingService;
import org.jumahuaca.examples.service.UVAScrapingServiceImpl;
import org.jumahuaca.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

@ExtendWith(SpringExtension.class)
public class SpringScraperServiceTests {

	private static final Integer TEST_MONTH = 4;
	private static final Integer TEST_YEAR = 2016;

	private UVAScrapingService service;

	@MockBean
	private UVAScrapingProcessRepository scrapingProcessRepository;

	@MockBean
	private UvaExchangeRepository exchangeRepository;

	@MockBean
	private UVAScrapingProcess mockedProcess;

	@MockBean
	private UVAScraper mockedScraper;

	private UVAScraper scraper;

	private static List<UVAExchange> fakedUvaExchanges;

	@BeforeEach
	public void setup() {
		service = new UVAScrapingServiceImpl(scrapingProcessRepository, exchangeRepository);
		scraper = new UVAScraper();
		fakedUvaExchanges = fakeSomeUVAExchanges();
	}

	@IntegrationTest
	public void testServiceScraperWithScrapingShouldWork() {
		MonthYear monthYear = new MonthYear(TEST_MONTH, TEST_YEAR);
		stubProcessUpdate(mockedProcess);
		stubFindUvaNotFound();
		service.programScrapping(monthYear, scraper, mockedProcess);
		verifyAllUvas(fakedUvaExchanges);
	}

	@IntegrationTest
	public void testServiceScraperWithScrapingShouldReWrite() {
		MonthYear monthYear = new MonthYear(TEST_MONTH, TEST_YEAR);
		stubProcessUpdate(mockedProcess);
		stubFindUvaFound(fakedUvaExchanges);
		service.programScrapping(monthYear, scraper, mockedProcess);
		verifyDeleteUvas(fakedUvaExchanges);
		verifyAllUvas(fakedUvaExchanges);
	}

	@Test
	public void testServiceScraperWithoutScrapingShouldWork() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		MonthYear monthYear = new MonthYear(TEST_MONTH, TEST_YEAR);
		stubProcessUpdate(mockedProcess);
		stubFindUvaNotFound();
		stubScraping(TEST_YEAR, TEST_MONTH);
		service.programScrapping(monthYear, mockedScraper, mockedProcess);
		verifyAllUvas(fakedUvaExchanges);
	}


	@Test
	public void testServiceScraperWithoutScrapingShouldReWrite() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		MonthYear monthYear = new MonthYear(TEST_MONTH, TEST_YEAR);
		stubProcessUpdate(mockedProcess);
		stubFindUvaFound(fakedUvaExchanges);
		stubScraping(TEST_YEAR, TEST_MONTH);
		service.programScrapping(monthYear, mockedScraper, mockedProcess);
		verifyDeleteUvas(fakedUvaExchanges);
		verifyAllUvas(fakedUvaExchanges);
	}
	

	private void stubScraping(Integer year, Integer month)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		when(mockedScraper.scrap(year, month)).thenReturn(fakedUvaExchanges);

	}

	private void verifyAllUvas(List<UVAExchange> fakedUvaExchanges) {
		for (UVAExchange uvaExchange : fakedUvaExchanges) {
			verify(exchangeRepository).save(uvaExchange);
		}
	}

	private void verifyDeleteUvas(List<UVAExchange> fakedUvaExchanges) {
		for (UVAExchange uvaExchange : fakedUvaExchanges) {
			verify(exchangeRepository).delete((uvaExchange));
		}
	}

	private void stubProcessUpdate(UVAScrapingProcess mockedProcess) {
		when(scrapingProcessRepository.save(mockedProcess)).thenReturn(mockedProcess);

	}

	private void stubFindUvaNotFound() {
		Optional<UVAExchange> result = Optional.empty();
		when(exchangeRepository.findById(any(LocalDate.class))).thenReturn(result);
	}

	private void stubFindUvaFound(List<UVAExchange> fakedUvaExchanges) {
		for (UVAExchange uvaExchange : fakedUvaExchanges) {
			Optional<UVAExchange> result = Optional.of(uvaExchange);
			when(exchangeRepository.findById(uvaExchange.getDate())).thenReturn(result);
		}

	}

	private static List<UVAExchange> fakeSomeUVAExchanges() {
		List<UVAExchange> exchanges = new ArrayList<UVAExchange>();
//		exchanges.add(new UVAExchange(LocalDate.parse("16/05/2019", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
//				BigDecimal.valueOf(36.3)));
		exchanges.add(new UVAExchange(LocalDate.parse("15/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.61)));
		exchanges.add(new UVAExchange(LocalDate.parse("14/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.61)));
		exchanges.add(new UVAExchange(LocalDate.parse("13/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.6)));
		exchanges.add(new UVAExchange(LocalDate.parse("12/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.58)));
		exchanges.add(new UVAExchange(LocalDate.parse("11/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.57)));
		exchanges.add(new UVAExchange(LocalDate.parse("10/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.55)));
		exchanges.add(new UVAExchange(LocalDate.parse("09/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.51)));
		exchanges.add(new UVAExchange(LocalDate.parse("08/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.51)));
		exchanges.add(new UVAExchange(LocalDate.parse("07/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.51)));
		exchanges.add(new UVAExchange(LocalDate.parse("06/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.5)));
		exchanges.add(new UVAExchange(LocalDate.parse("05/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.48)));
		exchanges.add(new UVAExchange(LocalDate.parse("04/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.47)));
		exchanges.add(new UVAExchange(LocalDate.parse("03/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.46)));
		exchanges.add(new UVAExchange(LocalDate.parse("02/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.41)));
		exchanges.add(new UVAExchange(LocalDate.parse("01/05/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.41)));
		exchanges.add(new UVAExchange(LocalDate.parse("30/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.41)));
		exchanges.add(new UVAExchange(LocalDate.parse("29/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.4)));
		exchanges.add(new UVAExchange(LocalDate.parse("28/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.38)));
		exchanges.add(new UVAExchange(LocalDate.parse("27/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.37)));
		exchanges.add(new UVAExchange(LocalDate.parse("26/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.36)));
		exchanges.add(new UVAExchange(LocalDate.parse("25/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.32)));
		exchanges.add(new UVAExchange(LocalDate.parse("24/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.32)));
		exchanges.add(new UVAExchange(LocalDate.parse("23/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.32)));
		exchanges.add(new UVAExchange(LocalDate.parse("22/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.31)));
		exchanges.add(new UVAExchange(LocalDate.parse("21/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.29)));
		exchanges.add(new UVAExchange(LocalDate.parse("20/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.28)));
		exchanges.add(new UVAExchange(LocalDate.parse("19/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.27)));
		exchanges.add(new UVAExchange(LocalDate.parse("18/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.23)));
		exchanges.add(new UVAExchange(LocalDate.parse("17/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.23)));
		exchanges.add(new UVAExchange(LocalDate.parse("16/04/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				BigDecimal.valueOf(14.23)));
		Collections.sort(exchanges, (a, b) -> a.getDate().compareTo(b.getDate()));
		return exchanges;
	}

}
