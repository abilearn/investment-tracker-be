package com.vaa.investment.tracker.investment_service.dto;

import com.vaa.investment.tracker.investment_service.entity.InvestmentStatus;
import com.vaa.investment.tracker.investment_service.entity.InvestmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvestmentRequest {

    @NotNull
    private InvestmentType type;

    @NotBlank
    private String organisationName;

    @NotBlank
    private String accountName;

    @NotBlank
    private String accountId;

    @NotNull
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
