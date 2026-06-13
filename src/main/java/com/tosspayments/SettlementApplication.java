package com.tosspayments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // ← 이거 추가! BaseEntity의 @CreatedDate 동작하게 함
@ConfigurationPropertiesScan
public class SettlementApplication {
	public static void main(String[] args) {
		SpringApplication.run(SettlementApplication.class, args);
	}
}

