package com.tosspayments.settlement.domain.payment.entity;

import com.tosspayments.settlement.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 결제 Entity
 * - 토스페이먼츠로부터 결제승인을 받은 건을 저장
 * - 정산의 원천 데이터
 */
@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA는 기본생성자 필수, 외부에서 직접 new 방지
@ToString
public class Payment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // MySQL AUTO_INCREMENT
	private Long id;

	/**
	 * 토스페이먼츠가 발급하는 고유 결제키
	 * 환불/조회 시 사용하는 핵심 식별자
	 */
	@Column(nullable = false, unique = true, length = 200)
	private String paymentKey;

	/**
	 * 가맹점이 생성한 주문번호
	 * 토스 대사 시 매핑 기준
	 */
	@Column(nullable = false, unique = true, length = 64)
	private String orderId;

	@Column(nullable = false, length = 100)
	private String orderName;

	/**
	 * 결제금액 - BigDecimal 사용 필수!
	 * double/float은 부동소수점 오차로 금융계산에 절대 사용 금지
	 * MSSQL의 DECIMAL(18,2) 와 동일
	 */
	@Column(nullable = false, precision = 18, scale = 2)
	private BigDecimal amount;

	/**
	 * 결제수단: CARD, VIRTUAL_ACCOUNT, EASY_PAY 등
	 */
	@Column(length = 20)
	private String method;

	@Enumerated(EnumType.STRING)  // DB에 숫자(0,1)가 아닌 문자열로 저장
	@Column(nullable = false, length = 20)
	private PaymentStatus status;

	private String merchantId;  // 가맹점 ID

	private LocalDateTime requestedAt;  // 결제 요청시각 (토스 응답값)
	private LocalDateTime approvedAt;   // 결제 승인시각 (토스 응답값)

	// ──────────────────────────────────────────
	// 정적 팩토리 메서드 패턴
	// new Payment(...) 대신 Payment.create(...) 사용
	// 생성 의도를 명확히 하고, 유효성 검증 추가 용이
	// ──────────────────────────────────────────
	public static Payment create(
			String paymentKey,
			String orderId,
			String orderName,
			BigDecimal amount,
			String method,
			String merchantId,
			LocalDateTime requestedAt,
			LocalDateTime approvedAt
	) {
		Payment payment = new Payment();
		payment.paymentKey = paymentKey;
		payment.orderId = orderId;
		payment.orderName = orderName;
		payment.amount = amount;
		payment.method = method;
		payment.status = PaymentStatus.DONE;
		payment.merchantId = merchantId;
		payment.requestedAt = requestedAt;
		payment.approvedAt = approvedAt;
		return payment;
	}

	public void cancel() {
		this.status = PaymentStatus.CANCELED;
	}
}