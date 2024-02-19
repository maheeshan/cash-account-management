package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;

import lombok.Data;

@Data
class TaxReliefDTO implements Serializable {

	private String taxReliefId;
	private Boolean isCondoUpgrade;
	private String creditorFiscalCode;
	private String beneficiaryType;
	private NaturalBeneficiaryDTO naturalPersonBeneficiary;
	private LegalBeneficiaryDTO legalPersonBeneficiary;
}
