package org.jumahuaca.examples.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "uva_loan_fee")
public class UVALoanFee implements Serializable {

	private static final long serialVersionUID = 2434823504265587585L;

	@EmbeddedId
	private UVALoanFeeId id;
	
	@Column(name = "loan_id", insertable=false, updatable=false)
	private Integer loanId;
	
	@Column(name = "fee_date")
	private LocalDate feeDate;

	@Column(name = "initial_capital")
	private BigDecimal initialCapital;

	@Column(name = "initial_interest")
	private BigDecimal initialInterest;

	@Column(name = "initial_total")
	private BigDecimal initialTotal;

	@Column(name = "final_capital")
	private BigDecimal finalCapital;

	@Column(name = "final_interest")
	private BigDecimal finalInterest;

	@Column(name = "final_total")
	private BigDecimal finalTotal;

	@ManyToOne
	@JoinColumn(name = "loan_id", referencedColumnName = "id", insertable = false, updatable = false)
	private UVALoan loan;

	public UVALoanFeeId getId() {
		return id;
	}

	public void setId(UVALoanFeeId id) {
		this.id = id;
	}
	
	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public BigDecimal getInitialCapital() {
		return initialCapital;
	}

	public void setInitialCapital(BigDecimal initialCapital) {
		this.initialCapital = initialCapital;
	}

	public BigDecimal getInitialInterest() {
		return initialInterest;
	}

	public void setInitialInterest(BigDecimal initialInterest) {
		this.initialInterest = initialInterest;
	}

	public BigDecimal getInitialTotal() {
		return initialTotal;
	}

	public void setInitialTotal(BigDecimal initialTotal) {
		this.initialTotal = initialTotal;
	}

	public BigDecimal getFinalCapital() {
		return finalCapital;
	}

	public void setFinalCapital(BigDecimal finalCapital) {
		this.finalCapital = finalCapital;
	}

	public BigDecimal getFinalInterest() {
		return finalInterest;
	}

	public void setFinalInterest(BigDecimal finalInterest) {
		this.finalInterest = finalInterest;
	}

	public BigDecimal getFinalTotal() {
		return finalTotal;
	}

	public void setFinalTotal(BigDecimal finalTotal) {
		this.finalTotal = finalTotal;
	}

	public UVALoan getLoan() {
		return loan;
	}

	public void setLoan(UVALoan loan) {
		this.loan = loan;
	}
	
	public LocalDate getFeeDate() {
		return feeDate;
	}

	public void setFeeDate(LocalDate feeDate) {
		this.feeDate = feeDate;
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
		UVALoanFee other = (UVALoanFee) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
