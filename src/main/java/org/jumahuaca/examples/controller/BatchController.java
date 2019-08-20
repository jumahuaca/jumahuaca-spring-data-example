package org.jumahuaca.examples.controller;

import static org.jumahuaca.examples.controller.PathConstants.BATCH_ROOT_PATH;
import static org.jumahuaca.examples.controller.PathConstants.RESOURCE_VERSION;
import static org.jumahuaca.examples.controller.PathConstants.UVA_UPDATE_FEES_POST_PATH;

import java.util.logging.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RESOURCE_VERSION+BATCH_ROOT_PATH)
public class BatchController {
	
	private final static Logger LOGGER = Logger.getLogger(BatchController.class.toString());
	
	@Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    @Qualifier("feeUpdateJob")
    Job feeUpdateJob;    
	
	@RequestMapping(method = RequestMethod.POST, value = UVA_UPDATE_FEES_POST_PATH)
	public ResponseEntity<String> updateFeed(@RequestBody Integer loanId) {
		JobParameters jobParameters = new JobParametersBuilder()
            	.addLong("loanId", Long.valueOf(loanId))
            	.toJobParameters();
            try {
				jobLauncher.run(feeUpdateJob, jobParameters);
				return new ResponseEntity<String>("Batch running", HttpStatus.OK);
			} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
					| JobParametersInvalidException e) {
				LOGGER.severe("Error running batch: " + e.getMessage());
				return new ResponseEntity<String>("Error running batch", HttpStatus.INTERNAL_SERVER_ERROR);

			}
	}

}
