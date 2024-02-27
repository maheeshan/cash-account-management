package com.amaris.cashaccountmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amaris.cashaccountmanagement.dto.AccountBalanceDTO;
import com.amaris.cashaccountmanagement.dto.AccountDTO;
import com.amaris.cashaccountmanagement.dto.ListWrapper;
import com.amaris.cashaccountmanagement.dto.ResponseWrapper;
import com.amaris.cashaccountmanagement.dto.TransactionDTO;
import com.amaris.cashaccountmanagement.dto.TransferRequestDTO;
import com.amaris.cashaccountmanagement.dto.TransferResponseDTO;
import com.amaris.cashaccountmanagement.service.IAccountManagementService;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cash-account")
@RequiredArgsConstructor
public class AccountManagementController {

	private final IAccountManagementService accountManagementService;

	@GetMapping("accounts")
	public ResponseWrapper<ListWrapper<AccountDTO>> getAllAccounts() {
		return accountManagementService.getAllAccounts();
	}

	@GetMapping("balance/{accountId}")
	public ResponseWrapper<AccountBalanceDTO> getAccountBalance(@PathVariable("accountId") String accountId) {
		return accountManagementService.getAccountBalance(accountId);
	}

	@GetMapping("transactions/{accountId}")
	public ResponseWrapper<ListWrapper<TransactionDTO>> getTransactions(@PathVariable("accountId") String accountId,
			@Nonnull @RequestParam("fromDate") String fromDate,
			@Nonnull @RequestParam("toDate") String toDate) {
		return accountManagementService.getTransactions(accountId, fromDate, toDate);
	}

	@PostMapping("payments/transfer/{accountId}")
	public ResponseWrapper<TransferResponseDTO> transfer(@PathVariable("accountId") String accountId,
			@RequestBody TransferRequestDTO transferRequest
	) {
		return accountManagementService.transfer(accountId, transferRequest);
	}

}
