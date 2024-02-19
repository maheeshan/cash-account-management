package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;

import lombok.Data;

@Data
class TransferAccountDTO implements Serializable {

	private String accountCode;
	private String bicCode;
}
