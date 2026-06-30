package com.vaa.investment.tracker.investmentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.vaa.investment.tracker")
@org.springframework.boot.autoconfigure.domain.EntityScan(basePackages = "com.vaa.investment.tracker")
@org.springframework.data.jpa.repository.config.EnableJpaRepositories(basePackages = "com.vaa.investment.tracker")
public class InvestmentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestmentManagementApplication.class, args);
    }

}
