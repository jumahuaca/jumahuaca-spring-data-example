package org.jumahuaca.examples.service;

import java.util.List;
import java.util.Optional;

import org.jumahuaca.examples.beans.MonthYear;
import org.jumahuaca.examples.entity.UVAExchange;
import org.jumahuaca.examples.entity.UVAScrapingProcess;
import org.jumahuaca.examples.entity.UVAScrapingProcessStatus;
import org.jumahuaca.examples.repository.UVAScrapingProcessRepository;
import org.jumahuaca.examples.repository.UvaExchangeRepository;
import org.jumahuaca.examples.scraper.UVAScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UVAScrapingServiceImpl implements UVAScrapingService{
	
	@Autowired
	private UVAScrapingProcessRepository scrapingProcessRepository;
	
	@Autowired
	private UvaExchangeRepository exchangeRepository;
	
	public UVAScrapingServiceImpl() {
		super();
	}

	public UVAScrapingServiceImpl(UVAScrapingProcessRepository scrapingProcessRepository,
			UvaExchangeRepository exchangeRepository) {
		super();
		this.scrapingProcessRepository = scrapingProcessRepository;
		this.exchangeRepository = exchangeRepository;
	}

	@Async
	@Override
	public void programScrapping(MonthYear monthYear, UVAScraper scraper, UVAScrapingProcess process) {
		try {
			List<UVAExchange> result = null;
			process.setStatus(UVAScrapingProcessStatus.RUNNING.name());
			scrapingProcessRepository.save(process);
			result = scraper.scrap(monthYear.getYear(), monthYear.getMonth());
			for (UVAExchange uvaExchange : result) {
					Optional<UVAExchange> exchange = exchangeRepository.findById(uvaExchange.getDate());
					if(exchange.isPresent()) {
						exchangeRepository.delete(exchange.get());
					}					
					exchangeRepository.save(uvaExchange);				
			}
			process.setStatus(UVAScrapingProcessStatus.FINISHED.name());
			scrapingProcessRepository.save(process);			
		} catch (Exception e) {
			process.setStatus(UVAScrapingProcessStatus.ERROR.name());
			scrapingProcessRepository.save(process);
		}
		
	}

}
