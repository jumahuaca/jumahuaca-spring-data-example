package org.jumahuaca.examples.app;

import static org.jumahuaca.assertions.HttpResponseStatusAssert.assertResponse200;
import static org.jumahuaca.assertions.HttpResponseStatusAssert.assertResponse500;
import static org.jumahuaca.examples.controller.PathConstants.UVA_SCRAPER_POST_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_SCRAPER_ROOT_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.jumahuaca.examples.beans.MonthYear;
import org.jumahuaca.examples.controller.PathConstants;
import org.jumahuaca.examples.controller.UVAScraperController;
import org.jumahuaca.examples.entity.UVAScrapingProcess;
import org.jumahuaca.examples.entity.UVAScrapingProcessStatus;
import org.jumahuaca.examples.repository.UVAScrapingProcessRepository;
import org.jumahuaca.examples.repository.UvaExchangeRepository;
import org.jumahuaca.examples.scraper.UVAScraper;
import org.jumahuaca.examples.service.UVAScrapingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UVAScraperController.class)
public class SpringScraperResourceTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UVAScrapingService service;
	
	@MockBean
	private UvaExchangeRepository uvaExchangeRepository;
	
	@MockBean
	private UVAScrapingProcessRepository uvaScrapingProcessRepository;
	
	private static final String PREFIX = PathConstants.RESOURCE_VERSION+UVA_SCRAPER_ROOT_PATH;
	
	
	private static final Integer TEST_MONTH = 4;
	private static final Integer TEST_YEAR = 2016;
	private static final Integer TEST_DAY = 16;


	@Test			
	public void scrapOneMonthShouldWork() throws JsonProcessingException, Exception {
		
		MonthYear monthYear = new MonthYear(TEST_MONTH,TEST_YEAR);
		UVAScrapingProcess process = new UVAScrapingProcess();
		LocalDate from = LocalDate.of(monthYear.getYear(), monthYear.getMonth(), TEST_DAY);
		process.setFromDate(from);
		process.setToDate(from.minusDays(1).plusMonths(1));
		process.setStatus(UVAScrapingProcessStatus.CREATED.name());
		process.setProcessDate(LocalDateTime.now());
		
		stubProcessCreateOk(process);
		stubServiceOk(monthYear,process);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX+UVA_SCRAPER_POST_PATH)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(monthYear))).andReturn();
		
		
		HttpServletResponse response = result.getResponse();
		assertResponse200(response);		
		
	}
	
	@Test			
	public void scrapOneMonthShouldFail() throws JsonProcessingException, Exception {
		
		MonthYear monthYear = new MonthYear(TEST_MONTH,TEST_YEAR);
		UVAScrapingProcess process = new UVAScrapingProcess();
		LocalDate from = LocalDate.of(monthYear.getYear(), monthYear.getMonth(), TEST_DAY);
		process.setFromDate(from);
		process.setToDate(from.minusDays(1).plusMonths(1));
		process.setStatus(UVAScrapingProcessStatus.CREATED.name());
		process.setProcessDate(LocalDateTime.now());
		
		stubProcessCreateError(process);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX+UVA_SCRAPER_POST_PATH)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(monthYear))).andReturn();
		
		
		HttpServletResponse response = result.getResponse();
		assertResponse500(response);		
		
	}
	
	private void stubProcessCreateError(UVAScrapingProcess process) {
		when(uvaScrapingProcessRepository.save(process)).thenThrow(RuntimeException.class);
		
	}

	private void stubServiceOk(MonthYear monthYear,UVAScrapingProcess process) {
		doNothing()
		.when(service).programScrapping(any(MonthYear.class), any(UVAScraper.class), any(UVAScrapingProcess.class));
	}


	private void stubProcessCreateOk(UVAScrapingProcess process) throws CloneNotSupportedException {
		UVAScrapingProcess cloned = (UVAScrapingProcess) process.clone();
		cloned.setId(1);
		when(uvaScrapingProcessRepository.save(process)).thenReturn(cloned);
	}
	
}
