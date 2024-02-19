package com.amaris.cashaccountmanagement.service;

import com.amaris.cashaccountmanagement.dto.AccountBalanceDTO;
import com.amaris.cashaccountmanagement.dto.AccountDTO;
import com.amaris.cashaccountmanagement.dto.ListWrapper;
import com.amaris.cashaccountmanagement.dto.ResponseWrapper;
import com.amaris.cashaccountmanagement.dto.TransactionDTO;
import com.amaris.cashaccountmanagement.dto.TransferRequestDTO;
import com.amaris.cashaccountmanagement.dto.TransferResponseDTO;

public interface IAccountManagementService {

	ResponseWrapper<ListWrapper<AccountDTO>> getAllAccounts();

	ResponseWrapper<AccountBalanceDTO> getAccountBalance(String accountId);

	ResponseWrapper<ListWrapper<TransactionDTO>> getTransactions(String accountId, String fromDate, String toDate);

	ResponseWrapper<TransferResponseDTO> transfer(String accountId, TransferRequestDTO transferRequest);

}
