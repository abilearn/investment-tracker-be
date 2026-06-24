package com.vaa.investment.tracker.investment_service.service;


import com.vaa.investment.tracker.investment_service.dto.UserRequest;
import com.vaa.investment.tracker.investment_service.dto.UserResponse;
import com.vaa.investment.tracker.investment_service.entity.User;
import com.vaa.investment.tracker.investment_service.entity.VerificationToken;
import com.vaa.investment.tracker.investment_service.repository.UserRepository;
import com.vaa.investment.tracker.investment_service.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;

    public UserResponse createUser(UserRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
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


        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public UserResponse updateUser(Long id, UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());


        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
