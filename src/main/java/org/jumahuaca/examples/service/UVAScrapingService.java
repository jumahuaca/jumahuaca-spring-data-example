package org.jumahuaca.examples.service;

import org.jumahuaca.examples.beans.MonthYear;
import org.jumahuaca.examples.entity.UVAScrapingProcess;
import org.jumahuaca.examples.scraper.UVAScraper;

public interface UVAScrapingService {
	
	void programScrapping(MonthYear monthYear, UVAScraper scraper, UVAScrapingProcess process);

}
