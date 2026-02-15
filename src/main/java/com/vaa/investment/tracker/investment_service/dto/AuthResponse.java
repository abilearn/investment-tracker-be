package com.vaa.investment.tracker.investment_service.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String token;
    private String email;
    private String name;
}
