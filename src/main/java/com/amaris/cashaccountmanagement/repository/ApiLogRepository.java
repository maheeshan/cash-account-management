package com.amaris.cashaccountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amaris.cashaccountmanagement.entity.ApiAccessRecord;

@Repository
public interface ApiLogRepository extends JpaRepository<ApiAccessRecord, Long> {
}
