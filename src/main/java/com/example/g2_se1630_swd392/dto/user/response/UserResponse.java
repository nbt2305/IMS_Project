package com.example.g2_se1630_swd392.dto.user.response;

import com.example.g2_se1630_swd392.entity.SystemSetting;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String picture;
    private String phoneNumber;
    private String name;
    private Boolean active;
    private SystemSetting role;
}
