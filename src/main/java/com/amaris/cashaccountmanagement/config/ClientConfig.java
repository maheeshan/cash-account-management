package com.amaris.cashaccountmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import com.amaris.cashaccountmanagement.dto.ResponseWrapper;
import com.amaris.cashaccountmanagement.exception.FabrickClientException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ClientConfig {

	private final FabrickClientProperty fabrickClientProperty;

	@Bean
	WebClient fabricClient() {
		return WebClient.builder()
				.baseUrl(fabrickClientProperty.getBaseUrl())
				.defaultHeader("Auth-Schema", fabrickClientProperty.getAuthSchema())
				.defaultHeader("Api-Key", fabrickClientProperty.getApiKey())
				.defaultStatusHandler(HttpStatusCode::isError, response -> {
					log.error("call to Fabric API in error {}", response);
					return response.bodyToMono(new ParameterizedTypeReference<ResponseWrapper<Object>>() {
					}).flatMap(body -> Mono.error(new FabrickClientException(body.getStatus(), body.getErrors())));
				})
				.build();
	}
}
