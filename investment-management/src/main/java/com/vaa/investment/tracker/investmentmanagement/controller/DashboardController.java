package com.vaa.investment.tracker.investmentmanagement.controller;


import com.vaa.investment.tracker.investmentmanagement.dto.DashboardResponse;
import com.vaa.investment.tracker.common.entity.User;
import com.vaa.investment.tracker.common.repository.UserRepository;
import com.vaa.investment.tracker.investmentmanagement.service.DashboardService;
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
    private final UserRepository userRepository;

    @GetMapping("/summary")
    public ResponseEntity<DashboardResponse> getDashboardSummary(Authentication authentication) {

        String email = authentication.getName();
        //Querying database after each login can be avoided (introduce cache mechanism next)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DashboardResponse response =
                dashboardService.getSummary(user.getId());

        return ResponseEntity.ok(response);
    }
}