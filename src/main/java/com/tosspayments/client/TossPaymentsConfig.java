package com.tosspayments.client;

import com.tosspayments.client.TossPaymentsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class TossPaymentsConfig {

    /**
     * 토스페이먼츠 전용 WebClient Bean.
     * baseUrl과 인증 헤더를 미리 세팅해두고 재사용한다.
     */
    @Bean
    public WebClient tossPaymentsWebClient(TossPaymentsProperties properties) {
        // 시크릿키 + ":" 를 Base64 인코딩 → Basic 인증 헤더 생성
        String encodedAuth = Base64.getEncoder()
                .encodeToString((properties.secretKey() + ":").getBytes(StandardCharsets.UTF_8));

        return WebClient.builder()
                .baseUrl(properties.baseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}