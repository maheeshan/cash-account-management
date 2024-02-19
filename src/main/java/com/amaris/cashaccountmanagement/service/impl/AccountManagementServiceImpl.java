package com.amaris.cashaccountmanagement.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.amaris.cashaccountmanagement.dto.AccountBalanceDTO;
import com.amaris.cashaccountmanagement.dto.AccountDTO;
import com.amaris.cashaccountmanagement.dto.ListWrapper;
import com.amaris.cashaccountmanagement.dto.ResponseWrapper;
import com.amaris.cashaccountmanagement.dto.TransactionDTO;
import com.amaris.cashaccountmanagement.dto.TransferRequestDTO;
import com.amaris.cashaccountmanagement.dto.TransferResponseDTO;
import com.amaris.cashaccountmanagement.service.IAccountManagementService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountManagementServiceImpl implements IAccountManagementService {

	private final WebClient fabrickClient;

	@Override
	public ResponseWrapper<ListWrapper<AccountDTO>> getAllAccounts() {
		log.info("Calling Fabrick API for get all accounts");
		return fabrickClient.get()
				.uri("accounts")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<ResponseWrapper<ListWrapper<AccountDTO>>>() {
				})
				.block();

	}

	@Override
	@SneakyThrows
	public ResponseWrapper<AccountBalanceDTO> getAccountBalance(String accountId) {
		log.info("Calling Fabrick API for get account balance for id: {}", accountId);
		return fabrickClient.get()
				.uri("accounts/{accountId}/balance", accountId)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<ResponseWrapper<AccountBalanceDTO>>() {
				})
				.block();
	}

	@Override
	public ResponseWrapper<ListWrapper<TransactionDTO>> getTransactions(String accountId, String fromDate, String toDate) {
		log.info("Calling Fabrick API for get all transaction for id: {}", accountId);
		return fabrickClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("accounts/{accountId}/transactions")
						.queryParam("fromAccountingDate", fromDate)
						.queryParam("toAccountingDate", toDate)
						.build(accountId))
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<ResponseWrapper<ListWrapper<TransactionDTO>>>() {
				})
				.block();
	}

	@Override
	public ResponseWrapper<TransferResponseDTO> transfer(String accountId, TransferRequestDTO transferRequest) {
		log.info("Calling Fabrick API for making a transfer from account: {}", accountId);
		return fabrickClient.post()
				.uri("accounts/{accountId}/payments/money-transfers", accountId)
				.body(Mono.just(transferRequest), TransferRequestDTO.class)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<ResponseWrapper<TransferResponseDTO>>() {
				})
				.block();
	}
}
