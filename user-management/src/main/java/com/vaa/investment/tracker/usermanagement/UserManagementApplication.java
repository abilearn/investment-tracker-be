package com.vaa.investment.tracker.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.vaa.investment.tracker")
@org.springframework.boot.autoconfigure.domain.EntityScan(basePackages = "com.vaa.investment.tracker")
@org.springframework.data.jpa.repository.config.EnableJpaRepositories(basePackages = "com.vaa.investment.tracker")
public class UserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

}
