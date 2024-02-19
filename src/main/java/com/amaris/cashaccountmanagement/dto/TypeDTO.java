package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;

import lombok.Data;

@Data
class TypeDTO implements Serializable {
	private String enumeration;
	private String value;
}
