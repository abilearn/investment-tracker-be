package com.vaa.investment.tracker.investment_service.service;

import com.vaa.investment.tracker.investment_service.dto.AuthRequest;
import com.vaa.investment.tracker.investment_service.dto.AuthResponse;
import com.vaa.investment.tracker.investment_service.entity.Role;
import com.vaa.investment.tracker.investment_service.entity.User;
import com.vaa.investment.tracker.investment_service.entity.VerificationToken;
import com.vaa.investment.tracker.investment_service.repository.UserRepository;
import com.vaa.investment.tracker.investment_service.repository.VerificationTokenRepository;
import com.vaa.investment.tracker.investment_service.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private static final Logger log =
            LoggerFactory.getLogger(AuthService.class);




    // ---------------- Register ----------------

    public AuthResponse register(AuthRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(false);
        String verificationToken = UUID.randomUUID().toString();
        emailService.sendVerificationEmail(
                user.getEmail(),
                verificationToken
        );

        VerificationToken token = new VerificationToken();
        token.setToken(verificationToken);
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusHours(24));

        verificationTokenRepository.save(token);
        userRepository.save(user);


        return AuthResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public void verifyEmail(String token) {

        VerificationToken verificationToken =
                verificationTokenRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Invalid token"));

        if (verificationToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Token expired");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);

        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);
    }
    // ---------------- Login ----------------
    public AuthResponse login(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole().name())
                        .build()
        );

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}



