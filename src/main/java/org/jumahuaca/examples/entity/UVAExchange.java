package org.jumahuaca.examples.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="uva_exchange")
public class UVAExchange implements Serializable{

	private static final long serialVersionUID = -2822020561399430311L;

	@Id
	@Column(name="exchange_day")
	private LocalDate date;

	@Column(name="rate")
	private BigDecimal rate;

	public UVAExchange() {
		super();
	}

	public UVAExchange(LocalDate date, BigDecimal rate) {
		super();
		this.date = date;
		this.rate = rate;
	}

	@JsonProperty
	public LocalDate getDate() {
		return date;
	}

	@JsonProperty
	public BigDecimal getRate() {
		return rate;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		UVAExchange other = (UVAExchange) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

}
