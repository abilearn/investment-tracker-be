package com.vaa.investment.tracker.investment_service.controller;


import com.vaa.investment.tracker.investment_service.dto.DashboardResponse;
import com.vaa.investment.tracker.investment_service.entity.User;
import com.vaa.investment.tracker.investment_service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardResponse> getDashboardSummary(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        DashboardResponse response =
                dashboardService.getSummary(user.getId());

        return ResponseEntity.ok(response);
    }
}