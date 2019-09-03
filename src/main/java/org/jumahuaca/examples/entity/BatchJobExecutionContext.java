package org.jumahuaca.examples.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH_JOB_EXECUTION_CONTEXT")
public class BatchJobExecutionContext implements Serializable {

	private static final long serialVersionUID = -5860638676672200841L;

	@Id
	@Column(name = "JOB_EXECUTION_ID")
	private Long jobExecutionId;

	@Column(name = "SHORT_CONTEXT")
	private String shortContext;

	@Column(name = "SERIALIZED_CONTEXT")
	private String serializedContext;

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public String getShortContext() {
		return shortContext;
	}

	public void setShortContext(String shortContext) {
		this.shortContext = shortContext;
	}

	public String getSerializedContext() {
		return serializedContext;
	}

	public void setSerializedContext(String serializedContext) {
		this.serializedContext = serializedContext;
	}

}
