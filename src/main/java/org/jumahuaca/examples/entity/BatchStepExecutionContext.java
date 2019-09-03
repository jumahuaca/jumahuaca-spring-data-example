package org.jumahuaca.examples.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH_STEP_EXECUTION_CONTEXT")
public class BatchStepExecutionContext implements Serializable {

	private static final long serialVersionUID = -2766022683853401500L;

	@Id
	@Column(name = "STEP_EXECUTION_ID")
	private Long stepExecutionId;

	@Column(name = "SHORT_CONTEXT")
	private String shortContext;

	@Column(name = "SERIALIZED_CONTEXT")
	private String serializedContext;

	public Long getStepExecutionId() {
		return stepExecutionId;
	}

	public void setStepExecutionId(Long stepExecutionId) {
		this.stepExecutionId = stepExecutionId;
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
