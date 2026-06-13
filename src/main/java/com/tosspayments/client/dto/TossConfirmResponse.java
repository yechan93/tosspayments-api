package com.tosspayments.client.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * 토스페이먼츠 결제 승인 응답.
 * 실제 응답 필드는 매우 많지만, 우리 도메인에 필요한 것만 매핑한다.
 * (JSON에 없는 필드는 무시되고, 객체에 없는 JSON 필드도 무시됨)
 */
public record TossConfirmResponse(
        String paymentKey,
        String orderId,
        String orderName,
        BigDecimal totalAmount,
        String method,
        String status,
        OffsetDateTime requestedAt,
        OffsetDateTime approvedAt
) {
}