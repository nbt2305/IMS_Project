package com.example.g2_se1630_swd392.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordOfForgotPassRequest {
    private String email;
    private String password;
}
