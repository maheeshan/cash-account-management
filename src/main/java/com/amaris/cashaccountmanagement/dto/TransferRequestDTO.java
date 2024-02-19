package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.amaris.cashaccountmanagement.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferRequestDTO implements Serializable {

	private CreditorDTO creditor;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.FABRICK_DATE_FORMAT)
	private LocalDate executionDate;
	private String uri;
	private String description;
	private Number amount;
	private String currency;
	private String feeType;
	private String feeAccountId;
	private TaxReliefDTO taxRelief;
}
