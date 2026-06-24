package com.vaa.investment.tracker.investment_service.dto;

import com.vaa.investment.tracker.investment_service.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
