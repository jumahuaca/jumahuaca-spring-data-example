package org.jumahuaca.examples.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH_JOB_INSTANCE")
public class BatchJobInstance implements Serializable {

	private static final long serialVersionUID = -4798792940097794944L;

	@Id
	@Column(name = "JOB_INSTANCE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_JOB_SEQ_GEN")
	@SequenceGenerator(name="BATCH_JOB_SEQ_GEN", sequenceName = "BATCH_JOB_SEQ")
	private Long jobInstanceId;

	@Column(name = "VERSION")
	private Long version;

	@Column(name = "JOB_NAME")
	private String jobName;

	@Column(name = "JOB_KEY")
	private String jobKey;

	public Long getJobInstanceId() {
		return jobInstanceId;
	}

	public void setJobInstanceId(Long jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobKey() {
		return jobKey;
	}

	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

}
