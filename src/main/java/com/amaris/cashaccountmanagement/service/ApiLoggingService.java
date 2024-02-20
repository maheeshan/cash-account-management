package com.amaris.cashaccountmanagement.service;

import com.amaris.cashaccountmanagement.dto.ApiAccessRecordDTO;

public interface ApiLoggingService {

	void logAccess(ApiAccessRecordDTO accessRecord);
}
