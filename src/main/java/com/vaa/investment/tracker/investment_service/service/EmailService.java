package com.vaa.investment.tracker.investment_service.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private static final Logger log =
            LoggerFactory.getLogger(EmailService.class);

    public void sendVerificationEmail(String email, String token) {

        String verificationLink =
                "http://localhost:8080/api/auth/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Email Verification");
        message.setText(
                "Welcome!\n\n" +
                        "Please verify your email by clicking the link below:\n\n" +
                        verificationLink
        );

        try {
            log.info("Sending verification email to {}", email);

            mailSender.send(message);

            log.info("Verification email sent successfully to {}", email);

        } catch (Exception ex) {

            log.error("Failed to send email to {}", email, ex);

            throw new RuntimeException(
                    "Unable to send verification email for " + email,
                    ex
            );
        }
    }
}