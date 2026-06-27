package com.vaa.investment.tracker.investment_service.dto;

import com.vaa.investment.tracker.investment_service.entity.InvestmentStatus;
import com.vaa.investment.tracker.investment_service.entity.InvestmentType;
import com.vaa.investment.tracker.investment_service.enums.RiskLevel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvestmentRequest {

    @NotNull(message = "Investment type is required")
    private InvestmentType type;

    @NotBlank(message = "Organisation name cannot be blank")
    @Size(max = 100, message = "Organisation name cannot exceed 100 characters")
    private String organisationName;

    @NotBlank(message = "Account name cannot be blank")
    @Size(max = 100, message = "Account name cannot exceed 100 characters")
    private String accountName;

    @NotBlank(message = "Account ID cannot be blank")
    @Size(max = 50, message = "Account ID cannot exceed 50 characters")
    private String accountId;

    @NotNull(message = "Invested amount is required")
    @DecimalMin(value = "0.01", message = "Invested amount must be greater than 0")
    private BigDecimal investedAmount;

    @DecimalMin(value = "0.0", inclusive = true, message = "Current value cannot be negative")
    private BigDecimal currentValue;

    @DecimalMin(value = "0.0", inclusive = true, message = "Matured amount cannot be negative")
    private BigDecimal maturedAmount;

    @DecimalMin(value = "0.0", inclusive = true, message = "Interest rate cannot be negative")
    private BigDecimal interestRate;

    @Positive(message = "Tenure must be greater than 0 months")
    private Integer tenureMonths;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate maturityDate;

    @NotNull(message = "Investment status is required")
    private InvestmentStatus status;

    @Size(max = 100, message = "Nominee name cannot exceed 100 characters")
    private String nomineeName;

    @Size(max = 50, message = "Portfolio tag cannot exceed 50 characters")
    private String portfolioTag;


    private RiskLevel riskLevel;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;
}
