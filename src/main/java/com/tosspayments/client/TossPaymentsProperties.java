package com.tosspayments.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * application.yml의 toss.payments.* 값을 매핑하는 설정 클래스.
 * record로 선언하면 불변(immutable) 설정 객체가 된다.
 */
@ConfigurationProperties(prefix = "toss.payments")
public record TossPaymentsProperties(
        String baseUrl,
        String secretKey
) {
}