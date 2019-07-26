package org.jumahuaca.examples.repository;

import org.jumahuaca.examples.entity.UVAScrapingProcess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UVAScrapingProcessRepository extends CrudRepository<UVAScrapingProcess, Integer>{

}
