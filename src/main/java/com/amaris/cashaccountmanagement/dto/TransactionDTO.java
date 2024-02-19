package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransactionDTO implements Serializable {

	private String transactionId;
	private String operationId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate accountingDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate valueDate;
	private TypeDTO type;
	private String amount;
	private String currency;
	private String description;

}
