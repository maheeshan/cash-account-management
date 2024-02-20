package com.amaris.cashaccountmanagement.config;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.amaris.cashaccountmanagement.dto.ResponseWrapper;
import com.amaris.cashaccountmanagement.exception.FabrickClientException;
import com.amaris.cashaccountmanagement.service.ApiLoggingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ClientConfig {

	private final FabrickClientProperty fabrickClientProperty;

	@Bean
	WebClient fabricClient(ApiLoggingService loggingService) {
		return WebClient.builder()
				.baseUrl(fabrickClientProperty.getBaseUrl())
				.defaultHeader("Auth-Schema", fabrickClientProperty.getAuthSchema())
				.defaultHeader("Api-Key", fabrickClientProperty.getApiKey())
				.defaultStatusHandler(HttpStatusCode::isError, response -> {
					log.error("call to Fabric API in error {}", response);
					return response.bodyToMono(new ParameterizedTypeReference<ResponseWrapper<Object>>() {
					}).flatMap(body -> Mono.error(new FabrickClientException(body.getStatus(), body.getErrors())));
				})
//				.filters(exchangeFilterFunctions -> {
//					exchangeFilterFunctions.add(logRequest(loggingService));
//					exchangeFilterFunctions.add(logResponse(loggingService));
//				})
				.build();
	}

//	ExchangeFilterFunction logRequest(ApiLoggingService loggingService) {
//		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
//			loggingService.logApiRequest(String.valueOf(clientRequest.method()),
//					clientRequest.body().toString(), clientRequest.url().toString());
//			return Mono.just(clientRequest);
//		});
//	}
//
//	ExchangeFilterFunction logResponse(ApiLoggingService loggingService) {
//		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
//			loggingService.logApiResponse(clientResponse.bodyToMono(String.class).toString(),
//					clientResponse.statusCode().toString());
//			return Mono.just(clientResponse);
//		});
//	}
}
