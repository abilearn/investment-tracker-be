package com.vaa.investment.tracker.investmentmanagement.service;

import com.vaa.investment.tracker.investmentmanagement.dto.InvestmentRequest;
import com.vaa.investment.tracker.investmentmanagement.dto.InvestmentResponse;
import com.vaa.investment.tracker.investmentmanagement.entity.Investment;
import com.vaa.investment.tracker.common.entity.User;
import com.vaa.investment.tracker.investmentmanagement.repository.InvestmentRepository;
import com.vaa.investment.tracker.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private static final Logger log =
            LoggerFactory.getLogger(InvestmentService.class);

    private final InvestmentRepository investmentRepository;
    private final UserRepository userRepository;

    // ---------------- Create ----------------
    public InvestmentResponse createInvestment(UserDetails userDetails,
                                               InvestmentRequest request) {

        log.info("===== CREATE INVESTMENT =====");
        log.info("Authenticated User: {}", userDetails.getUsername());

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found: {}", userDetails.getUsername());
                    return new RuntimeException("User not found");
                });

        log.info("User found: {} ({})", user.getName(), user.getEmail());

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
        inv.setRiskLevel(String.valueOf(request.getRiskLevel()));
        inv.setNotes(request.getNotes());
        inv.setCreatedAt(LocalDateTime.now());

        investmentRepository.save(inv);

        log.info("Investment saved successfully.");
        log.info("Investment Id: {}", inv.getId());

        return mapToResponse(inv);
    }

    // ---------------- Get All ----------------
    public List<InvestmentResponse> getAllInvestments(UserDetails userDetails) {

        log.info("===== GET ALL INVESTMENTS =====");
        log.info("Authenticated User: {}", userDetails.getUsername());

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found: {}", userDetails.getUsername());
                    return new RuntimeException("User not found");
                });

        List<Investment> investments =
                investmentRepository.findByUserAndActiveTrue(user);

        log.info("Found {} investments for {}",
                investments.size(),
                user.getEmail());

        return investments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ---------------- Get by ID ----------------
    public InvestmentResponse getInvestmentById(UserDetails userDetails,
                                                Long id) {

        log.info("===== GET INVESTMENT BY ID =====");
        log.info("Investment Id: {}", id);
        log.info("Authenticated User: {}", userDetails.getUsername());

        Investment inv = investmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Investment not found: {}", id);
                    return new RuntimeException("Investment not found");
                });

        if (!inv.getUser().getEmail().equals(userDetails.getUsername())) {

            log.warn("Unauthorized access.");
            log.warn("Logged in User : {}", userDetails.getUsername());
            log.warn("Investment User: {}", inv.getUser().getEmail());

            throw new RuntimeException("Unauthorized");
        }

        log.info("Investment found.");

        return mapToResponse(inv);
    }

    // ---------------- Update ----------------
    public InvestmentResponse updateInvestment(UserDetails userDetails,
                                               Long id,
                                               InvestmentRequest request) {

        log.info("===== UPDATE INVESTMENT =====");
        log.info("Investment Id: {}", id);
        log.info("Authenticated User: {}", userDetails.getUsername());

        Investment inv = investmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Investment not found: {}", id);
                    return new RuntimeException("Investment not found");
                });

        if (!inv.getUser().getEmail().equals(userDetails.getUsername())) {

            log.warn("Unauthorized update.");
            log.warn("Logged in User : {}", userDetails.getUsername());
            log.warn("Investment User: {}", inv.getUser().getEmail());

            throw new RuntimeException("Unauthorized");
        }

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
        inv.setRiskLevel(String.valueOf(request.getRiskLevel()));
        inv.setNotes(request.getNotes());
        inv.setUpdatedAt(LocalDateTime.now());

        investmentRepository.save(inv);

        log.info("Investment updated successfully.");

        return mapToResponse(inv);
    }

    // ---------------- Delete ----------------
    public void deleteInvestment(UserDetails userDetails,
                                 Long id) {

        log.info("===== DELETE INVESTMENT =====");
        log.info("Investment Id: {}", id);
        log.info("Authenticated User: {}", userDetails.getUsername());

        Investment inv = investmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Investment not found: {}", id);
                    return new RuntimeException("Investment not found");
                });

        if (!inv.getUser().getEmail().equals(userDetails.getUsername())) {

            log.warn("Unauthorized delete.");
            log.warn("Logged in User : {}", userDetails.getUsername());
            log.warn("Investment User: {}", inv.getUser().getEmail());

            throw new RuntimeException("Unauthorized");
        }

        inv.setActive(false);
        inv.setUpdatedAt(LocalDateTime.now());

        investmentRepository.save(inv);

        log.info("Investment soft deleted successfully.");
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