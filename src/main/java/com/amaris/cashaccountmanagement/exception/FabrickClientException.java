package com.amaris.cashaccountmanagement.exception;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FabrickClientException extends RuntimeException {

	private String status;
	private List<Object> errors;

	public FabrickClientException(String status, List<Object> errors) {
		this.status = status;
		this.errors = errors;
	}
}
