package com.example.g2_se1630_swd392.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum G2_SWD392_Error {
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"), // request sai trang thai
    USER_USERNAME_PASSWORD_ERROR("USER_USERNAME_PASSWORD_ERROR"), // Username password ko hợp lệ
    USER_USERNAME_ALREADY_ERROR("USER_USERNAME_PASSWORD_ERROR"), // Username password ko hợp lệ
    USER_NOT_FOUND_ERROR("USER_NOT_FOUND_ERROR"); // call otp error

    private final String message;
}
