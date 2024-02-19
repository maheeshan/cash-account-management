package com.amaris.cashaccountmanagement;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.amaris.cashaccountmanagement.dto.ResponseWrapper;
import com.amaris.cashaccountmanagement.exception.FabrickClientException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value
			= {FabrickClientException.class})
	protected ResponseEntity<Object> handleConflict(
			FabrickClientException ex, WebRequest request) {
		ResponseWrapper<Object> bodyOfResponse = new ResponseWrapper<>(ex.getStatus(), ex.getErrors(), null);
		return handleExceptionInternal(ex, bodyOfResponse,
				new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}
