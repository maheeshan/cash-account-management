package com.amaris.cashaccountmanagement.service.impl;

import org.springframework.stereotype.Service;

import com.amaris.cashaccountmanagement.dto.ApiAccessRecordDTO;
import com.amaris.cashaccountmanagement.entity.ApiAccessRecord;
import com.amaris.cashaccountmanagement.repository.ApiLogRepository;
import com.amaris.cashaccountmanagement.service.ApiLoggingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiLoggingServiceImpl implements ApiLoggingService {

	private final ApiLogRepository apiLogRepository;

	@Override
	public void logAccess(ApiAccessRecordDTO accessRecord) {

		apiLogRepository.save(
				ApiAccessRecord.builder()
						.requestTimeStamp(accessRecord.getRequestTimeStamp())
						.httpMethod(accessRecord.getHttpMethod())
						.endpoint(accessRecord.getEndpoint())
						.clientIp(accessRecord.getClientIp())
						.requestBody(accessRecord.getRequestBody())
						.responseCode(accessRecord.getResponseCode())
						.responseBody(accessRecord.getResponseBody())
						.build()
		);
		log.info("log saved success");
	}
}
