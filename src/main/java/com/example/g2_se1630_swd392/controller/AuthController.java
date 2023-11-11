package com.example.g2_se1630_swd392.controller;

import com.example.g2_se1630_swd392.dto.HttpResponse;
import com.example.g2_se1630_swd392.dto.user.request.GoogleLoginRequest;
import com.example.g2_se1630_swd392.dto.user.request.LoginRequest;
import com.example.g2_se1630_swd392.service.UserService;
import com.example.g2_se1630_swd392.dto.user.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Trung Nguyễn Bá
 * @created 9/27/2023
 * @project IMS_G2_SWD392
 */

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class AuthController {
    private final UserService userService;
    @PostMapping("/login/google")
    public HttpResponse<LoginResponse> singleSignOnGoogle(@RequestBody GoogleLoginRequest request) {
        return HttpResponse.ok(userService.singleSignOnGoogle(request));
    }

    @PostMapping("/login/email-password")
    public HttpResponse<LoginResponse> loginByUsernamePass(@RequestBody LoginRequest request) {
        return HttpResponse.ok(userService.loginByUsernamePass(request));
    }

}
