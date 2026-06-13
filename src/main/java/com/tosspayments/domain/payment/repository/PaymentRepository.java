package com.tosspayments.domain.payment.repository;

import com.tosspayments.domain.payment.entity.Payment;
import com.tosspayments.domain.payment.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 1. paymentKey로 단건 조회 (토스 결제 고유키)
    Optional<Payment> findByPaymentKey(String paymentKey);

    // 2. orderId로 단건 조회 (주문 고유번호)
    Optional<Payment> findByOrderId(String orderId);

    // 3. 특정 가맹점의 특정 날짜 범위 결제 내역 조회 (정산 배치에서 사용)
    List<Payment> findByMerchantIdAndStatusAndApprovedAtBetween(
            String merchantId,
            PaymentStatus status,
            LocalDateTime start,
            LocalDateTime end
    );

    // 4. paymentKey 중복 체크
    boolean existsByPaymentKey(String paymentKey);
}

