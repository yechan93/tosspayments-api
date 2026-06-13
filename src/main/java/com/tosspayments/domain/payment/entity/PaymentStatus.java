package com.tosspayments.domain.payment.entity;

/**
 * 결제 상태
 * 토스페이먼츠 API 응답의 status 값과 동일하게 맞춤
 */
public enum PaymentStatus {
	READY,      // 결제 준비
	IN_PROGRESS,// 결제 진행 중
	DONE,       // 결제 완료 (정산 대상)
	CANCELED,   // 결제 취소
	ABORTED,    // 결제 중단
	EXPIRED     // 결제 만료
}

