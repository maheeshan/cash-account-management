package com.amaris.cashaccountmanagement.controller;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amaris.cashaccountmanagement.dto.TransferRequestDTO;
import com.amaris.cashaccountmanagement.service.IAccountManagementService;

@ExtendWith(MockitoExtension.class)
class TestAccountManagementController {

	public static final String ACCOUNT_ID = "1234";
	@Mock
	IAccountManagementService accountManagementService;
	@InjectMocks
	AccountManagementController accountManagementController;

	@Test
	void getAllAccounts() {
		//when
		accountManagementController.getAllAccounts();
		//then
		verify(accountManagementService).getAllAccounts();
	}

	@Test
	void getAccountBalance() {
		//when
		accountManagementController.getAccountBalance(ACCOUNT_ID);
		//then
		verify(accountManagementService).getAccountBalance(ACCOUNT_ID);
	}

	@Test
	void getTransactions() {
		//given
		var fromDate = "2021-02-02";
		var toDate = "2021-02-20";
		//when
		accountManagementController.getTransactions(ACCOUNT_ID, fromDate, toDate);
		//then
		verify(accountManagementService).getTransactions(ACCOUNT_ID, fromDate, toDate);
	}

	@Test
	void transfer() {
		//given
		var transferRequest = TransferRequestDTO.builder().build();
		//when
		accountManagementController.transfer(ACCOUNT_ID, transferRequest);
		//then
		verify(accountManagementService).transfer(ACCOUNT_ID, transferRequest);
	}
}
