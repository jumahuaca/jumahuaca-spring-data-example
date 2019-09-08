package org.jumahuaca.examples.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH_JOB_EXECUTION")
public class BatchJobExecution implements Serializable {

	private static final long serialVersionUID = -3704003567594236886L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_JOB_EXECUTION_SEQ_GEN")
	@SequenceGenerator(name="BATCH_JOB_EXECUTION_SEQ_GEN", sequenceName = "BATCH_JOB_EXECUTION_SEQ")
	@Column(name = "JOB_EXECUTION_ID")
	private Long jobExecutionId;

	@Column(name = "VERSION")
	private Long version;

	@Column(name = "JOB_INSTANCE_ID")
	private Long jobInstanceId;

	@Column(name = "CREATE_TIME")
	private LocalDateTime createTime;

	@Column(name = "START_TIME")
	private LocalDateTime startTime;

	@Column(name = "END_TIME")
	private LocalDateTime endTime;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "EXIT_CODE")
	private String exitCode;

	@Column(name = "EXIT_MESSAGE")
	private String exitMessage;

	@Column(name = "LAST_UPDATED")
	private String lastUpdated;

	@Column(name = "JOB_CONFIGURATION_LOCATION")
	private String jobConfigurationLocation;

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getJobInstanceId() {
		return jobInstanceId;
	}

	public void setJobInstanceId(Long jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExitCode() {
		return exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public String getExitMessage() {
		return exitMessage;
	}

	public void setExitMessage(String exitMessage) {
		this.exitMessage = exitMessage;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getJobConfigurationLocation() {
		return jobConfigurationLocation;
	}

	public void setJobConfigurationLocation(String jobConfigurationLocation) {
		this.jobConfigurationLocation = jobConfigurationLocation;
	}

}
