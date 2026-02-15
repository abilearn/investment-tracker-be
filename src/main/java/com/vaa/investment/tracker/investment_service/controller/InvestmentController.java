package com.vaa.investment.tracker.investment_service.controller;


import com.vaa.investment.tracker.investment_service.dto.InvestmentRequest;
import com.vaa.investment.tracker.investment_service.dto.InvestmentResponse;
import com.vaa.investment.tracker.investment_service.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investments")
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;

    // ---------------- Create a new investment ----------------
    @PostMapping
    public ResponseEntity<InvestmentResponse> createInvestment(
            @AuthenticationPrincipal UserDetails userDetails,
            @Validated @RequestBody InvestmentRequest request) {

        InvestmentResponse response = investmentService.createInvestment(userDetails, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ---------------- Get all investments for logged-in user ----------------
    @GetMapping
    public ResponseEntity<List<InvestmentResponse>> getAllInvestments(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<InvestmentResponse> investments = investmentService.getAllInvestments(userDetails);
        return ResponseEntity.ok(investments);
    }

    // ---------------- Get investment by ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<InvestmentResponse> getInvestmentById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {

        InvestmentResponse response = investmentService.getInvestmentById(userDetails, id);
        return ResponseEntity.ok(response);
    }

    // ---------------- Update investment ----------------
    @PutMapping("/{id}")
    public ResponseEntity<InvestmentResponse> updateInvestment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Validated @RequestBody InvestmentRequest request) {

        InvestmentResponse response = investmentService.updateInvestment(userDetails, id, request);
        return ResponseEntity.ok(response);
    }

    // ---------------- Delete investment (soft delete) ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {

        investmentService.deleteInvestment(userDetails, id);
        return ResponseEntity.noContent().build();
    }
}
