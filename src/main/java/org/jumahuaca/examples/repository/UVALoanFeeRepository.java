package org.jumahuaca.examples.repository;

import java.util.List;

import org.jumahuaca.examples.entity.UVALoanFee;
import org.jumahuaca.examples.entity.UVALoanFeeId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UVALoanFeeRepository extends CrudRepository<UVALoanFee, UVALoanFeeId>{
	
	List<UVALoanFee> findByLoanId(Integer loanId);

}
