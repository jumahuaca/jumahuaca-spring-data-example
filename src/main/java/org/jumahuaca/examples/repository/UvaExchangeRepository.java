package org.jumahuaca.examples.repository;

import java.time.LocalDate;

import org.jumahuaca.examples.entity.UVAExchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UvaExchangeRepository extends CrudRepository<UVAExchange, LocalDate>{

}
