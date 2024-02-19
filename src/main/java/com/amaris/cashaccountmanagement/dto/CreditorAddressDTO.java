package com.amaris.cashaccountmanagement.dto;

import java.io.Serializable;

import lombok.Data;

@Data
class CreditorAddressDTO implements Serializable {

	private String address;
	private String city;
	private String countryCode;
}
