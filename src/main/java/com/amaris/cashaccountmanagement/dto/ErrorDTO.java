package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ErrorDTO implements Serializable {

	private String code;
	private String description;
	private String params;
}
