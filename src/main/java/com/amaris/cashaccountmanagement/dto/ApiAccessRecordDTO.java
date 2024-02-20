package com.amaris.cashaccountmanagement.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class ApiAccessRecordDTO {
	private Long id;
	private Timestamp requestTimeStamp;
	private String httpMethod;
	private String endpoint;
	private String clientIp;
	private String requestBody;
	private String responseCode;
	private String responseBody;
}
