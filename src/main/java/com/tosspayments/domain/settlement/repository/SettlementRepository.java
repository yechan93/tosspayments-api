package com.tosspayments.domain.settlement.repository;

import com.tosspayments.domain.settlement.entity.Settlement;
import com.tosspayments.domain.settlement.entity.SettlementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    // 1. 특정 날짜 + 가맹점의 정산 단건 조회
    //    → 복합 유니크 제약(settlement_date, merchant_id)에 대응
    Optional<Settlement> findBySettlementDateAndMerchantId(
            LocalDate settlementDate,
            String merchantId
    );

    // 2. 특정 날짜의 전체 가맹점 정산 목록 조회
    List<Settlement> findBySettlementDate(LocalDate settlementDate);

    // 3. 특정 가맹점의 날짜 범위 정산 내역 조회
    List<Settlement> findByMerchantIdAndSettlementDateBetween(
            String merchantId,
            LocalDate startDate,
            LocalDate endDate
    );

    // 4. 특정 날짜 + 가맹점 정산 존재 여부 확인 (중복 실행 방지용)
    boolean existsBySettlementDateAndMerchantId(
            LocalDate settlementDate,
            String merchantId
    );

    // 5. 특정 날짜의 정산 상태별 조회 (JPQL 사용 예시)
    @Query("SELECT s FROM Settlement s WHERE s.settlementDate = :date AND s.status = :status")
    List<Settlement> findByDateAndStatus(
            @Param("date") LocalDate date,
            @Param("status") SettlementStatus status
    );
}

