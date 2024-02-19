package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;

import lombok.Data;

@Data
class FeeDTO implements Serializable {
	private String feeCode;
	private String description;
	private double amount;
	private String currency;
}
