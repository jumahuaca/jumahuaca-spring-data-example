package org.jumahuaca.examples.springdata.controller;

import static org.jumahuaca.examples.springdata.controller.PathConstants.RESOURCE_VERSION;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jumahuaca.examples.springdata.entity.UVAExchange;
import org.jumahuaca.examples.springdata.repository.UvaExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RESOURCE_VERSION+"exchange")
public class UVAExchangeController {
	
	@Autowired
	private UvaExchangeRepository uvaExchangeRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "all")
	public ResponseEntity<List<UVAExchange>> getAllExchanges() {
		List<UVAExchange> result = (List<UVAExchange>) uvaExchangeRepository.findAll();
		if(result!=null && !result.isEmpty()) {
			return new ResponseEntity<List<UVAExchange>>(result, HttpStatus.OK);
		}else if(result.isEmpty()) {
			return new ResponseEntity<List<UVAExchange>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<UVAExchange>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{year}/{month}/{day}")
	public ResponseEntity<UVAExchange> getExchange(@PathVariable("year") Integer year, @PathVariable("month") Integer month,
			@PathVariable("day") Integer day) {
		LocalDate date = LocalDate.of(year, month, day);
		Optional<UVAExchange> result = uvaExchangeRepository.findById(date);
		if(result.isPresent()) {
			return new ResponseEntity<UVAExchange>(result.get(),HttpStatus.OK);
		}else {
			return new ResponseEntity<UVAExchange>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "")
	public ResponseEntity<UVAExchange> post(@RequestBody UVAExchange exchange) {
		try {
			UVAExchange result = uvaExchangeRepository.save(exchange);
			return new ResponseEntity<UVAExchange>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UVAExchange>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "")
	public ResponseEntity<UVAExchange> put(@RequestBody UVAExchange exchange) {
		try {
			UVAExchange result = uvaExchangeRepository.save(exchange);
			return new ResponseEntity<UVAExchange>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UVAExchange>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "")
	public ResponseEntity<String> remove(@RequestBody UVAExchange exchange) {
		try {
			uvaExchangeRepository.delete(exchange);
			return new ResponseEntity<String>("Exchange removed succesfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}	
	
	

}
