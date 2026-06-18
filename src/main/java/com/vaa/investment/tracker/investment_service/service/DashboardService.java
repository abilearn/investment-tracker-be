package com.vaa.investment.tracker.investment_service.service;

import com.vaa.investment.tracker.investment_service.dto.DashboardResponse;
import com.vaa.investment.tracker.investment_service.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final InvestmentRepository repository;

    public DashboardResponse getSummary(Long userId) {

        BigDecimal invested = repository.totalInvested(userId);
        BigDecimal current = repository.totalCurrentValue(userId);

        BigDecimal profit = current.subtract(invested);

        return DashboardResponse.builder()
                .totalInvested(invested)
                .currentValue(current)
                .totalProfit(profit)
                .activeInvestments(repository.activeCount(userId))
                .build();
    }
}