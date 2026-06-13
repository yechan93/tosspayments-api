package com.tosspayments.client;

import com.tosspayments.client.dto.TossConfirmRequest;
import com.tosspayments.client.dto.TossConfirmResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TossPaymentsClient {

    private final WebClient tossPaymentsWebClient;

    // 생성자 주입 (Spring이 위에서 만든 Bean을 넣어줌)
    public TossPaymentsClient(WebClient tossPaymentsWebClient) {
        this.tossPaymentsWebClient = tossPaymentsWebClient;
    }

    /**
     * 결제 승인 API 호출
     * POST https://api.tosspayments.com/v1/payments/confirm
     */
    public TossConfirmResponse confirmPayment(TossConfirmRequest request) {
        return tossPaymentsWebClient.post()
                .uri("/v1/payments/confirm")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TossConfirmResponse.class)
                .block();  // 동기 호출 (응답 올 때까지 대기)
    }
}