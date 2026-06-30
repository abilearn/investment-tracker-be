package com.vaa.investment.tracker.investmentmanagement.dto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class DashboardResponse {

    private BigDecimal totalInvested;
    private BigDecimal currentValue;
    private BigDecimal totalProfit;
    private Long activeInvestments;

}

