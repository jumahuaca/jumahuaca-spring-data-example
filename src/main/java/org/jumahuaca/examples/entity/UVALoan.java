package org.jumahuaca.examples.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "uva_loan")
public class UVALoan implements Serializable {

	private static final long serialVersionUID = 1326786363135797272L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "loan_date")
	private LocalDate loanDate;

	@Column(name = "loan_dni_holder")
	private Long holderDNI;

	@Column(name = "loan_dni_coholder")
	private Long coholderDNI;

	@Column(name = "pesos_value")
	private BigDecimal pesosValue;

	@Column(name = "uva_value")
	private BigDecimal uvaValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(LocalDate loanDate) {
		this.loanDate = loanDate;
	}

	public Long getHolderDNI() {
		return holderDNI;
	}

	public void setHolderDNI(Long holderDNI) {
		this.holderDNI = holderDNI;
	}

	public Long getCoholderDNI() {
		return coholderDNI;
	}

	public void setCoholderDNI(Long coholderDNI) {
		this.coholderDNI = coholderDNI;
	}

	public BigDecimal getPesosValue() {
		return pesosValue;
	}

	public void setPesosValue(BigDecimal pesosValue) {
		this.pesosValue = pesosValue;
	}

	public BigDecimal getUvaValue() {
		return uvaValue;
	}

	public void setUvaValue(BigDecimal uvaValue) {
		this.uvaValue = uvaValue;
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
		UVALoan other = (UVALoan) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
