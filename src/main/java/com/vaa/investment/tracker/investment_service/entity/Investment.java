package com.vaa.investment.tracker.investment_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InvestmentType type;

    private String organisationName;
    private String accountName;
    private String accountId;

    private BigDecimal investedAmount;
    private BigDecimal currentValue;
    private BigDecimal maturedAmount;

    private BigDecimal interestRate;
    private Integer tenureMonths;

    private LocalDate startDate;
    private LocalDate maturityDate;

    @Enumerated(EnumType.STRING)
    private InvestmentStatus status;

    private String nomineeName;
    private String portfolioTag;
    private String riskLevel;

    private String notes;

    private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
