package com.amaris.cashaccountmanagement.service.impl;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.amaris.cashaccountmanagement.dto.AccountBalanceDTO;
import com.amaris.cashaccountmanagement.dto.AccountDTO;
import com.amaris.cashaccountmanagement.dto.CreditorDTO;
import com.amaris.cashaccountmanagement.dto.ListWrapper;
import com.amaris.cashaccountmanagement.dto.ResponseWrapper;
import com.amaris.cashaccountmanagement.dto.TransactionDTO;
import com.amaris.cashaccountmanagement.dto.TransferRequestDTO;
import com.amaris.cashaccountmanagement.dto.TransferResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;

import lombok.SneakyThrows;

//@ExtendWith(SpringExtension.class)
@ExtendWith(SpringExtension.class)
class TestAccountManagementServiceImpl {

	public static final String ACCOUNT_ID = "1234";

	@TestConfiguration
	static class Config {

		@Bean
		public WireMockServer webServer() {
			WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
			// required so we can use `baseUrl()` in the construction of `webClient` below
			wireMockServer.start();
			return wireMockServer;
		}

		@Bean
		WebClient webClient(WireMockServer server) {
			return WebClient.builder().baseUrl(server.baseUrl()).build();
		}

		@Bean
		AccountManagementServiceImpl accountManagementService() {
			return new AccountManagementServiceImpl(webClient(webServer()));
		}

		@Bean
		ObjectMapper objectMapper() {
			var objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			return objectMapper;
		}
	}

	private static final EasyRandom generator = new EasyRandom();

	@Autowired
	private AccountManagementServiceImpl accountManagementService;
	@Autowired
	WireMockServer server;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getAllAccounts_test() {
		var accountDTOS = generator.objects(AccountDTO.class, 5).toList();

		server.stubFor(
				get(urlEqualTo("/accounts"))
						.willReturn(
								aResponse()
										.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
										.withBody(getResponseString(ListWrapper.<AccountDTO> builder().list(accountDTOS).build()))
						)
		);
		var allAccountsResponse = accountManagementService.getAllAccounts();

		Assertions.assertThat(allAccountsResponse.getPayload().getList()).hasSize(5);

	}

	@Test
	void getAccountBalance_test() {
		var accountBalanceDTO = AccountBalanceDTO.builder()
				.date(LocalDate.of(2021, 2, 3))
				.balance("1000")
				.availableBalance("900")
				.build();
		server.stubFor(
				get(urlEqualTo("/accounts/" + ACCOUNT_ID + "/balance"))
						.willReturn(
								aResponse()
										.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
										.withBody(getResponseString(accountBalanceDTO))
						)
		);
		var accountBalanceResponse = accountManagementService.getAccountBalance(ACCOUNT_ID);
		Assertions.assertThat(accountBalanceResponse.getPayload())
				.returns(accountBalanceDTO.getBalance(), AccountBalanceDTO::getBalance)
				.returns(accountBalanceDTO.getAvailableBalance(), AccountBalanceDTO::getAvailableBalance)
				.returns(accountBalanceDTO.getDate(), AccountBalanceDTO::getDate);
	}

	@Test
	void getTransactions() {
		var fromDate = "2021-1-2";
		var toDate = "2021-2-2";
		var transactionsDtos = generator.objects(TransactionDTO.class, 5).toList();
		server.stubFor(
				get(urlEqualTo("/accounts/" + ACCOUNT_ID + "/transactions?fromAccountingDate=" + fromDate + "&toAccountingDate=" + toDate))
						.willReturn(
								aResponse()
										.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
										.withBody(getResponseString(ListWrapper.<TransactionDTO> builder().list(transactionsDtos).build()))
						)
		);
		var transactionsResponse = accountManagementService.getTransactions(ACCOUNT_ID, fromDate, toDate);

		Assertions.assertThat(transactionsResponse.getPayload().getList()).hasSize(5);
	}

	@Test
	@SneakyThrows
	void transfer() {
		var moneyTransferId = "3241";
		var creditorName = "TEST";
		var transferDto = TransferResponseDTO.builder()
				.moneyTransferId(moneyTransferId)
				.build();
		var transferRequest = TransferRequestDTO.builder()
				.creditor(CreditorDTO.builder().name(creditorName).build())
				.build();

		server.stubFor(
				post(urlEqualTo("/accounts/" + ACCOUNT_ID + "/payments/money-transfers"))
						.withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_VALUE))
						.withRequestBody(containing("\"name\":\"TEST\""))
						.willReturn(
								aResponse()
										.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
										.withBody(getResponseString(transferDto))
						)
		);

		var transferResponse = accountManagementService.transfer(ACCOUNT_ID, transferRequest);
		Assertions.assertThat(transferResponse.getPayload()).returns(moneyTransferId, TransferResponseDTO::getMoneyTransferId);
	}

	@SneakyThrows
	private String getResponseString(Object payload) {
		var response = ResponseWrapper.builder()
				.status("OK")
				.errors(null)
				.payload(payload)
				.build();
		return objectMapper.writeValueAsString(response);
	}

}