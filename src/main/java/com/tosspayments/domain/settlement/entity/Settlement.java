package com.tosspayments.domain.settlement.entity;

import com.tosspayments.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 정산 Entity
 * - 배치 잡이 매일 새벽에 Payment를 집계하여 생성
 * - 1일 1가맹점 1레코드 (일별 정산)
 */
@Entity
@Table(name = "settlement",
		// 복합 유니크 제약: 같은 날, 같은 가맹점은 중복 정산 불가
		uniqueConstraints = @UniqueConstraint(
				columnNames = {"settlement_date", "merchant_id"}
		)
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Settlement extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 정산 기준일 (어제 날짜 결제건을 오늘 새벽 정산)
	 * LocalDate = 날짜만 (시간 없음) - MSSQL의 DATE 타입과 동일
	 */
	@Column(nullable = false)
	private LocalDate settlementDate;

	private String merchantId;  // 가맹점 ID

	/** 총 결제금액 */
	@Column(nullable = false, precision = 18, scale = 2)
	private BigDecimal totalAmount;

	/**
	 * 수수료 (예: 결제금액의 3.3%)
	 * 실무에서는 가맹점별 수수료율 테이블이 별도 존재
	 */
	@Column(nullable = false, precision = 18, scale = 2)
	private BigDecimal fee;

	/**
	 * 실지급액 = 총결제금액 - 수수료
	 * totalAmount - fee
	 */
	@Column(nullable = false, precision = 18, scale = 2)
	private BigDecimal netAmount;

	/** 정산 건수 */
	@Column(nullable = false)
	private Integer paymentCount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private SettlementStatus status;

	// ──────────────────────────────────────────
	// 정적 팩토리 메서드
	// ──────────────────────────────────────────
	public static Settlement create(
			LocalDate settlementDate,
			String merchantId,
			BigDecimal totalAmount,
			BigDecimal fee,
			Integer paymentCount
	) {
		Settlement settlement = new Settlement();
		settlement.settlementDate = settlementDate;
		settlement.merchantId = merchantId;
		settlement.totalAmount = totalAmount;
		settlement.fee = fee;
		// BigDecimal 연산: subtract = 빼기
		settlement.netAmount = totalAmount.subtract(fee);
		settlement.paymentCount = paymentCount;
		settlement.status = SettlementStatus.PENDING;
		return settlement;
	}

	/** 정산 완료 처리 */
	public void complete() {
		this.status = SettlementStatus.COMPLETED;
	}

	/** 정산 실패 처리 */
	public void fail() {
		this.status = SettlementStatus.FAILED;
	}
}

