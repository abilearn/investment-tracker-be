package com.vaa.investment.tracker.usermanagement.controller;

import com.vaa.investment.tracker.usermanagement.dto.AuthRequest;
import com.vaa.investment.tracker.usermanagement.dto.AuthResponse;
import com.vaa.investment.tracker.usermanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private static final Logger log =
            LoggerFactory.getLogger(AuthController.class);

    // ---------------- Register a new user ----------------
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Validated @RequestBody AuthRequest request) {

        AuthResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(
            @RequestParam String token) {

        authService.verifyEmail(token);

        return ResponseEntity.ok("Email verified successfully");
    }

    // ---------------- Login user ----------------
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Validated @RequestBody AuthRequest request) {

        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}


