package org.jumahuaca.examples.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH_STEP_EXECUTION")
public class BatchStepExecution implements Serializable {

	private static final long serialVersionUID = -1477245130152625398L;

	@Id
	@Column(name = "STEP_EXECUTION_ID")
	private Long stepExecutionId;

	@Column(name = "VERSION")
	private Long version;

	@Column(name = "STEP_NAME")
	private String stepName;

	@Column(name = "JOB_EXECUTION_ID")
	private Long jobExecutionId;

	@Column(name = "START_TIME")
	private LocalDateTime startTime;

	@Column(name = "END_TIME")
	private LocalDateTime endTime;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "COMMIT_COUNT")
	private Long commitCount;

	@Column(name = "READ_COUNT")
	private Long readCount;

	@Column(name = "FILTER_COUNT")
	private Long filterCount;

	@Column(name = "WRITE_COUNT")
	private Long writeCount;

	@Column(name = "READ_SKIP_COUNT")
	private Long readSkipCount;

	@Column(name = "WRITE_SKIP_COUNT")
	private Long writeSkipCount;

	@Column(name = "PROCESS_SKIP_COUNT")
	private Long processSkipCount;

	@Column(name = "ROLLBACK_COUNT")
	private Long rollbackCount;

	@Column(name = "EXIT_CODE")
	private String exitCode;

	@Column(name = "EXIT_MESSAGE")
	private String exitMessage;

	@Column(name = "LAST_UPDATED")
	private String lastUpdated;

	public Long getStepExecutionId() {
		return stepExecutionId;
	}

	public void setStepExecutionId(Long stepExecutionId) {
		this.stepExecutionId = stepExecutionId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
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

	public Long getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(Long commitCount) {
		this.commitCount = commitCount;
	}

	public Long getReadCount() {
		return readCount;
	}

	public void setReadCount(Long readCount) {
		this.readCount = readCount;
	}

	public Long getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(Long filterCount) {
		this.filterCount = filterCount;
	}

	public Long getWriteCount() {
		return writeCount;
	}

	public void setWriteCount(Long writeCount) {
		this.writeCount = writeCount;
	}

	public Long getReadSkipCount() {
		return readSkipCount;
	}

	public void setReadSkipCount(Long readSkipCount) {
		this.readSkipCount = readSkipCount;
	}

	public Long getWriteSkipCount() {
		return writeSkipCount;
	}

	public void setWriteSkipCount(Long writeSkipCount) {
		this.writeSkipCount = writeSkipCount;
	}

	public Long getProcessSkipCount() {
		return processSkipCount;
	}

	public void setProcessSkipCount(Long processSkipCount) {
		this.processSkipCount = processSkipCount;
	}

	public Long getRollbackCount() {
		return rollbackCount;
	}

	public void setRollbackCount(Long rollbackCount) {
		this.rollbackCount = rollbackCount;
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

}
