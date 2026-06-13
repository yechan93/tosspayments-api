package com.tosspayments.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 모든 Entity가 공통으로 상속받는 Auditing 클래스
 * - @CreatedDate : INSERT 시 자동으로 현재시각 저장
 * - @LastModifiedDate : UPDATE 시 자동으로 현재시각 저장
 * - MSSQL의 DEFAULT GETDATE() 와 같은 역할
 */
@Getter
@MappedSuperclass           // 테이블로 생성되지 않고 상속용으로만 사용
@EntityListeners(AuditingEntityListener.class)  // Auditing 활성화
public abstract class BaseEntity {

	@CreatedDate
	@Column(updatable = false)  // 최초 생성 후 변경 불가
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;
}

