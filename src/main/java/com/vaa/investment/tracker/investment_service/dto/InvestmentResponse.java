package com.vaa.investment.tracker.investment_service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
public class InvestmentResponse {

    private Long id;

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

    private InvestmentStatus status;

    private String nomineeName;
    private String portfolioTag;
    private String riskLevel;

    private String notes;
}
