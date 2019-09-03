package org.jumahuaca.examples.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH_JOB_EXECUTION_PARAMS")
public class BatchJobExecutionParams implements Serializable {

	private static final long serialVersionUID = -3110144390461917344L;

	@EmbeddedId
	private BatchJobExecutionParamsId id;

	@Column(name = "STRING_VAL")
	private String stringVal;

	@Column(name = "DATE_VAL")
	private LocalDateTime dateVal;

	@Column(name = "LONG_VAL")
	private Long longVal;

	@Column(name = "DOUBLE_VAL")
	private Double doubleVal;

	@Column(name = "IDENTIFYING")
	private String indtifyng;

	public BatchJobExecutionParamsId getId() {
		return id;
	}

	public void setId(BatchJobExecutionParamsId id) {
		this.id = id;
	}

	public String getStringVal() {
		return stringVal;
	}

	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}

	public LocalDateTime getDateVal() {
		return dateVal;
	}

	public void setDateVal(LocalDateTime dateVal) {
		this.dateVal = dateVal;
	}

	public Long getLongVal() {
		return longVal;
	}

	public void setLongVal(Long longVal) {
		this.longVal = longVal;
	}

	public Double getDoubleVal() {
		return doubleVal;
	}

	public void setDoubleVal(Double doubleVal) {
		this.doubleVal = doubleVal;
	}

	public String getIndtifyng() {
		return indtifyng;
	}

	public void setIndtifyng(String indtifyng) {
		this.indtifyng = indtifyng;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BatchJobExecutionParams other = (BatchJobExecutionParams) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
