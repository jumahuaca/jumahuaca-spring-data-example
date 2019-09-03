package org.jumahuaca.examples.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BatchJobExecutionParamsId implements Serializable {

	private static final long serialVersionUID = -952036076640747029L;

	@Column(name = "JOB_EXECUTION_ID")
	private Long jobExecutionId;

	@Column(name = "TYPE_CD")
	private String typeCd;

	@Column(name = "KEY_NAME")
	private String keyName;

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobExecutionId == null) ? 0 : jobExecutionId.hashCode());
		result = prime * result + ((keyName == null) ? 0 : keyName.hashCode());
		result = prime * result + ((typeCd == null) ? 0 : typeCd.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BatchJobExecutionParamsId other = (BatchJobExecutionParamsId) obj;
		if (jobExecutionId == null) {
			if (other.jobExecutionId != null)
				return false;
		} else if (!jobExecutionId.equals(other.jobExecutionId))
			return false;
		if (keyName == null) {
			if (other.keyName != null)
				return false;
		} else if (!keyName.equals(other.keyName))
			return false;
		if (typeCd == null) {
			if (other.typeCd != null)
				return false;
		} else if (!typeCd.equals(other.typeCd))
			return false;
		return true;
	}

}
