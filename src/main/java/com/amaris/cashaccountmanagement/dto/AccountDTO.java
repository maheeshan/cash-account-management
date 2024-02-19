package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;
import java.util.Date;

import com.amaris.cashaccountmanagement.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO implements Serializable {
	private String accountId;
	private String iban;
	private String cabCode;
	private String countryCode;
	private String internationalCin;
	private String nationalCin;
	private String account;
	private String alias;
	private String productName;
	private String holderName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.FABRICK_DATE_FORMAT)
	private Date activatedDate;
	private String currency;
}
