package com.tosspayments.client.dto;

import java.math.BigDecimal;

/** 토스페이먼츠 결제 승인 요청 바디 */
public record TossConfirmRequest(
        String paymentKey,
        String orderId,
        BigDecimal amount
) {
}