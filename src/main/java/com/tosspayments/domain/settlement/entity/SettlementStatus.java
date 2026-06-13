package com.tosspayments.domain.settlement.entity;

public enum SettlementStatus {
	PENDING,    // 정산 대기 (배치 실행 전)
	COMPLETED,  // 정산 완료
	FAILED      // 정산 실패 (배치 오류)
}

