package com.example.g2_se1630_swd392.controller;

import com.example.g2_se1630_swd392.dto.sms.SmsRequest;
import com.example.g2_se1630_swd392.dto.user.request.*;
import com.example.g2_se1630_swd392.dto.HttpResponse;
import com.example.g2_se1630_swd392.dto.user.response.UserResponse;
import com.example.g2_se1630_swd392.service.UserService;

import javax.mail.MessagingException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public HttpResponse<?> register(@RequestBody RegisterUserRequest request) {
        return HttpResponse.ok(userService.registerUser(request));
    }

    @PostMapping("/create")
    public HttpResponse<?> create(@RequestBody CreateUserRequest request) {
        return HttpResponse.ok(userService.create(request));
    }

    @PutMapping("/update/{id}")
    public HttpResponse<?> update(@PathVariable("id") Integer id, @RequestBody UpdateUserRequest request) {
        return HttpResponse.ok(userService.update(id, request));
    }

    @PutMapping("/forgot-password/change-password")
    public HttpResponse<?> changePassOfForgot(@RequestBody ChangePasswordOfForgotPassRequest request) {
        return HttpResponse.ok(userService.changePassOfForgotPass(request));
    }

    @GetMapping("/get-detail/{id}")
    public HttpResponse<?> getDetail(@PathVariable("id") Integer id) {
        return HttpResponse.ok(userService.getDetail(id));
    }

    @GetMapping("/get-all")
    @ApiOperation(value = "Get List User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserResponse.class),
            @ApiResponse(code = 403, message = "Access Denied")
    })
    public HttpResponse<?> getAll() {
        return HttpResponse.ok(userService.findAll());
    }

    @PostMapping("/send-email-code")
    public HttpResponse<String> sendEmailCode(@RequestBody SendEmailCodeRequest request) throws MessagingException, UnsupportedEncodingException {
        return HttpResponse.ok(userService.sendEmailCode(request));
    }

    @PostMapping("/send-email-code/register")
    public HttpResponse<String> sendEmailCodeForRegister(@RequestBody SendEmailCodeRequest request) throws MessagingException, UnsupportedEncodingException {
        return HttpResponse.ok(userService.sendEmailCodeForRegister(request));
    }

    @PostMapping("/send-sms")
    public HttpResponse<String> sendSms(@RequestBody SmsRequest request) {
        userService.sendSms(request.getEmail());
        return HttpResponse.ok("Done");
    }

    @PutMapping("/change-active/{id}")
    public HttpResponse<UserResponse> changeActive(@PathVariable("id") Integer id) {
        return HttpResponse.ok(userService.changeActive(id));
    }

    @PutMapping("/change-password/{id}")
    public HttpResponse<?> changePassword(@PathVariable("id") Integer id, @RequestBody ChangePasswordRequest request) {
        return HttpResponse.ok(userService.changePassword(id, request));
    }

}
