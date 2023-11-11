package com.example.g2_se1630_swd392.dto.user.response;

import com.example.g2_se1630_swd392.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class LoginResponse {
    private Boolean hasAccount;
    private User user;
    private String token;
    private String role;
    private List<String> permissions;
}
