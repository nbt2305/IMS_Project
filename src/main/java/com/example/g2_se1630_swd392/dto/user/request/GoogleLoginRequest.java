package com.example.g2_se1630_swd392.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleLoginRequest {
    private String username;
    private String password;
    private String email;
    private String picture;
    private String name;
    private Boolean emailVerified;
    private String phoneNumber;
    private Integer roleId;
}
