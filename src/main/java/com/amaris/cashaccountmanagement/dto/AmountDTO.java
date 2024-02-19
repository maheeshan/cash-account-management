package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;

import lombok.Data;

@Data
class AmountDTO implements Serializable {
	private double debtorAmount;
	private String debtorCurrency;
	private double creditorAmount;
	private String creditorCurrency;
	private String creditorCurrencyDate;
	private int exchangeRate;
}
