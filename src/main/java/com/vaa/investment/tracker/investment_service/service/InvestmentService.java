package com.vaa.investment.tracker.investment_service.service;

import com.vaa.investment.tracker.investment_service.dto.InvestmentRequest;
import com.vaa.investment.tracker.investment_service.dto.InvestmentResponse;
import com.vaa.investment.tracker.investment_service.entity.Investment;
import com.vaa.investment.tracker.investment_service.repository.InvestmentRepository;
import com.vaa.investment.tracker.investment_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final UserRepository userRepository;

    // ---------------- Create ----------------
    public InvestmentResponse createInvestment(UserDetails userDetails, InvestmentRequest request) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Investment inv = new Investment();
        inv.setUser(user);
        inv.setType(request.getType());
        inv.setOrganisationName(request.getOrganisationName());
        inv.setAccountName(request.getAccountName());
        inv.setAccountId(request.getAccountId());
        inv.setInvestedAmount(request.getInvestedAmount());
        inv.setCurrentValue(request.getCurrentValue());
        inv.setMaturedAmount(request.getMaturedAmount());
        inv.setInterestRate(request.getInterestRate());
        inv.setTenureMonths(request.getTenureMonths());
        inv.setStartDate(request.getStartDate());
        inv.setMaturityDate(request.getMaturityDate());
        inv.setStatus(request.getStatus());
        inv.setNomineeName(request.getNomineeName());
        inv.setPortfolioTag(request.getPortfolioTag());
        inv.setRiskLevel(request.getRiskLevel());
        inv.setNotes(request.getNotes());
        inv.setCreatedAt(LocalDateTime.now());

        investmentRepository.save(inv);

        return mapToResponse(inv);
    }

    // ---------------- Get All ----------------
    public List<InvestmentResponse> getAllInvestments(UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return investmentRepository.findByUserAndActiveTrue(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ---------------- Get by ID ----------------
    public InvestmentResponse getInvestmentById(UserDetails userDetails, Long id) {

        Investment inv = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found"));

        if (!inv.getUser().getEmail().equals(userDetails.getUsername()))
            throw new RuntimeException("Unauthorized");

        return mapToResponse(inv);
    }

    // ---------------- Update ----------------
    public InvestmentResponse updateInvestment(UserDetails userDetails, Long id, InvestmentRequest request) {

        Investment inv = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found"));

        if (!inv.getUser().getEmail().equals(userDetails.getUsername()))
            throw new RuntimeException("Unauthorized");

        inv.setType(request.getType());
        inv.setOrganisationName(request.getOrganisationName());
        inv.setAccountName(request.getAccountName());
        inv.setAccountId(request.getAccountId());
        inv.setInvestedAmount(request.getInvestedAmount());
        inv.setCurrentValue(request.getCurrentValue());
        inv.setMaturedAmount(request.getMaturedAmount());
        inv.setInterestRate(request.getInterestRate());
        inv.setTenureMonths(request.getTenureMonths());
        inv.setStartDate(request.getStartDate());
        inv.setMaturityDate(request.getMaturityDate());
        inv.setStatus(request.getStatus());
        inv.setNomineeName(request.getNomineeName());
        inv.setPortfolioTag(request.getPortfolioTag());
        inv.setRiskLevel(request.getRiskLevel());
        inv.setNotes(request.getNotes());
        inv.setUpdatedAt(LocalDateTime.now());

        investmentRepository.save(inv);

        return mapToResponse(inv);
    }

    // ---------------- Delete (Soft) ----------------
    public void deleteInvestment(UserDetails userDetails, Long id) {

        Investment inv = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found"));

        if (!inv.getUser().getEmail().equals(userDetails.getUsername()))
            throw new RuntimeException("Unauthorized");

        inv.setActive(false);
        inv.setUpdatedAt(LocalDateTime.now());

        investmentRepository.save(inv);
    }

    // ---------------- Mapper ----------------
    private InvestmentResponse mapToResponse(Investment inv) {
        return InvestmentResponse.builder()
                .id(inv.getId())
                .type(inv.getType())
                .organisationName(inv.getOrganisationName())
                .accountName(inv.getAccountName())
                .accountId(inv.getAccountId())
                .investedAmount(inv.getInvestedAmount())
                .currentValue(inv.getCurrentValue())
                .maturedAmount(inv.getMaturedAmount())
                .interestRate(inv.getInterestRate())
                .tenureMonths(inv.getTenureMonths())
                .startDate(inv.getStartDate())
                .maturityDate(inv.getMaturityDate())
                .status(inv.getStatus())
                .nomineeName(inv.getNomineeName())
                .portfolioTag(inv.getPortfolioTag())
                .riskLevel(inv.getRiskLevel())
                .notes(inv.getNotes())
                .build();
    }
}
