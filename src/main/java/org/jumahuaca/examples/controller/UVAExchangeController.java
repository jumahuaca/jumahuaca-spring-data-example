package org.jumahuaca.examples.controller;

import static org.jumahuaca.examples.controller.PathConstants.RESOURCE_VERSION;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_DAY_PARAM;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_GET_ALL_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_GET_ONE_PARAMS;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_MONTH_PARAM;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_POST_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_PUT_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_REMOVE_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_ROOT_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_YEAR_PARAM;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jumahuaca.examples.entity.UVAExchange;
import org.jumahuaca.examples.repository.UvaExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RESOURCE_VERSION+UVA_EXCHANGE_ROOT_PATH)
public class UVAExchangeController {
	
	@Autowired
	private UvaExchangeRepository uvaExchangeRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = UVA_EXCHANGE_GET_ALL_PATH)
	public ResponseEntity<List<UVAExchange>> getAllExchanges() {
		List<UVAExchange> result = (List<UVAExchange>) uvaExchangeRepository.findAll();
		if(result!=null && !result.isEmpty()) {
			return new ResponseEntity<List<UVAExchange>>(result, HttpStatus.OK);
		}else if(result!=null && result.isEmpty()) {
			return new ResponseEntity<List<UVAExchange>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<UVAExchange>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = UVA_EXCHANGE_GET_ONE_PARAMS)
	public ResponseEntity<UVAExchange> getExchange(@PathVariable(UVA_EXCHANGE_YEAR_PARAM) Integer year, @PathVariable(UVA_EXCHANGE_MONTH_PARAM) Integer month,
			@PathVariable(UVA_EXCHANGE_DAY_PARAM) Integer day) {
		LocalDate date = LocalDate.of(year, month, day);
		Optional<UVAExchange> result = uvaExchangeRepository.findById(date);
		if(result.isPresent()) {
			return new ResponseEntity<UVAExchange>(result.get(),HttpStatus.OK);
		}else {
			return new ResponseEntity<UVAExchange>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = UVA_EXCHANGE_POST_PATH)
	public ResponseEntity<UVAExchange> post(@RequestBody UVAExchange exchange) {
		try {
			UVAExchange result = uvaExchangeRepository.save(exchange);
			return new ResponseEntity<UVAExchange>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UVAExchange>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = UVA_EXCHANGE_PUT_PATH)
	public ResponseEntity<UVAExchange> put(@RequestBody UVAExchange exchange) {
		try {
			UVAExchange result = uvaExchangeRepository.save(exchange);
			return new ResponseEntity<UVAExchange>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UVAExchange>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = UVA_EXCHANGE_REMOVE_PATH)
	public ResponseEntity<String> remove(@RequestBody UVAExchange exchange) {
		try {
			uvaExchangeRepository.delete(exchange);
			return new ResponseEntity<String>("Exchange removed succesfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}	
	
	

}
