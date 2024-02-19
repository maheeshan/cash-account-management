package com.amaris.cashaccountmanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "fabrick")
@Getter
@Setter
public class FabrickClientProperty {
	private String baseUrl;
	private String authSchema;
	private String apiKey;
}
