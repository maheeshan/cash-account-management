package com.amaris.cashaccountmanagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseWrapper<T> {
	private String status;
	private List<Object> errors;
	private T payload;
}
