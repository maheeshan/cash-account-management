package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LegalBeneficiaryDTO implements Serializable {
	private String fiscalCode;
	private String legalRepresentativeFiscalCode;
}
