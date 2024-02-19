package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;
import java.time.LocalDate;

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
public class AccountBalanceDTO implements Serializable {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.FABRICK_DATE_FORMAT)
	private LocalDate date;
	private String balance;
	private String availableBalance;
}
