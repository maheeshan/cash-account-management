package com.amaris.cashaccountmanagement.config;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpClientTransport;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.http.HttpClientTransportOverHTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.amaris.cashaccountmanagement.dto.ApiAccessRecordDTO;
import com.amaris.cashaccountmanagement.dto.ResponseWrapper;
import com.amaris.cashaccountmanagement.exception.FabrickClientException;
import com.amaris.cashaccountmanagement.service.ApiLoggingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JettyWebClient {

	private final FabrickClientProperty fabrickClientProperty;
	private final ApiLoggingService apiLoggingService;

	@Bean
	public WebClient jettyHttpClient() {
		HttpClientTransport httpClientTransport = new HttpClientTransportOverHTTP();
		HttpClient httpClient = new HttpClient(httpClientTransport) {
			@Override
			public Request newRequest(URI uri) {
				Request request = super.newRequest(uri);
				return enhance(request);
			}
		};

		return WebClient.builder()
				.baseUrl(fabrickClientProperty.getBaseUrl())
				.defaultHeader("Auth-Schema", fabrickClientProperty.getAuthSchema())
				.defaultHeader("Api-Key", fabrickClientProperty.getApiKey())
				.defaultStatusHandler(HttpStatusCode::isError, response -> {
					log.error("call to Fabric API in error {}", response);
					return response.bodyToMono(new ParameterizedTypeReference<ResponseWrapper<Object>>() {
					}).flatMap(body -> Mono.error(new FabrickClientException(body.getStatus(), body.getErrors())));
				})
				.clientConnector(new JettyClientHttpConnector(httpClient))
				.build();
	}

	private Request enhance(Request inboundRequest) {
		var accessRecordDTOBuilder = ApiAccessRecordDTO.builder();
		// Request Logging
		inboundRequest.onRequestBegin(request -> {
					try {
						accessRecordDTOBuilder.requestTimeStamp(Timestamp.valueOf(LocalDateTime.now()))
								.httpMethod(request.getMethod())
								.endpoint(request.getURI().toString())
								.clientIp(InetAddress.getLocalHost().getHostAddress());
					}
					catch (UnknownHostException e) {
						throw new RuntimeException(e);
					}
				}
		);

		inboundRequest.onRequestContent((request, content) -> accessRecordDTOBuilder
				.requestBody(content.toString()));

		// Response Logging
		inboundRequest.onResponseBegin(response -> accessRecordDTOBuilder
				.responseCode(String.valueOf(response.getStatus()))

		);
		inboundRequest.onResponseContent(((response, content) -> {
			var bufferAsString = StandardCharsets.UTF_8.decode(content).toString();
			accessRecordDTOBuilder
					.responseBody(bufferAsString);
		}));

		inboundRequest.onComplete(request -> apiLoggingService.logAccess(accessRecordDTOBuilder.build()));
		//		apiLoggingService.logAccess();
		// Return original request
		return inboundRequest;
	}

}
