package org.jumahuaca.examples.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UVALoanFeeId implements Serializable {

	private static final long serialVersionUID = 391404092931848739L;

	@Column(name = "loan_id")
	private Integer loanId;

	@Column(name = "fee_number")
	private Integer feeNumber;

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getFeeNumber() {
		return feeNumber;
	}

	public void setFeeNumber(Integer feeNumber) {
		this.feeNumber = feeNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feeNumber == null) ? 0 : feeNumber.hashCode());
		result = prime * result + ((loanId == null) ? 0 : loanId.hashCode());
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
		UVALoanFeeId other = (UVALoanFeeId) obj;
		if (feeNumber == null) {
			if (other.feeNumber != null)
				return false;
		} else if (!feeNumber.equals(other.feeNumber))
			return false;
		if (loanId == null) {
			if (other.loanId != null)
				return false;
		} else if (!loanId.equals(other.loanId))
			return false;
		return true;
	}

}
