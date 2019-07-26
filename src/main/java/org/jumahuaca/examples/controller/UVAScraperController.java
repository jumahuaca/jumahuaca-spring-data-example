package org.jumahuaca.examples.controller;

import static org.jumahuaca.examples.controller.PathConstants.RESOURCE_VERSION;
import static org.jumahuaca.examples.controller.PathConstants.UVA_SCRAPER_POST_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_SCRAPER_ROOT_PATH;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.jumahuaca.examples.beans.MonthYear;
import org.jumahuaca.examples.entity.UVAScrapingProcess;
import org.jumahuaca.examples.entity.UVAScrapingProcessStatus;
import org.jumahuaca.examples.repository.UVAScrapingProcessRepository;
import org.jumahuaca.examples.scraper.UVAScraper;
import org.jumahuaca.examples.service.UVAScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RESOURCE_VERSION+UVA_SCRAPER_ROOT_PATH)
public class UVAScraperController {
	
	private final int FROM_PERIOD_DAY = 16;
	
	@Autowired
	private UVAScrapingProcessRepository scrapingProcessRepository;
	
	@Autowired
	private UVAScrapingService service;
	
	private UVAScraper scraper;
	
	public UVAScraperController() {
		scraper = new UVAScraper();
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = UVA_SCRAPER_POST_PATH)
	public ResponseEntity<String> scrapMonth(@RequestBody MonthYear monthYear) {
		UVAScrapingProcess process = new UVAScrapingProcess();
		LocalDate from = LocalDate.of(monthYear.getYear(), monthYear.getMonth(), FROM_PERIOD_DAY);
		process.setFromDate(from);
		process.setToDate(from.minusDays(1).plusMonths(1));
		process.setStatus(UVAScrapingProcessStatus.CREATED.name());
		process.setProcessDate(LocalDateTime.now());
		try {
			process = scrapingProcessRepository.save(process);
			service.programScrapping(monthYear, scraper,process);
			return new ResponseEntity<String>("Scraping programed",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Could not program scraping",HttpStatus.INTERNAL_SERVER_ERROR);

		}		
	}
	


}
