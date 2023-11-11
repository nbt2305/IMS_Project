package com.example.g2_se1630_swd392.service;


import com.example.g2_se1630_swd392.dto.user.request.*;
import com.example.g2_se1630_swd392.dto.user.response.LoginResponse;
import com.example.g2_se1630_swd392.dto.user.response.UserResponse;
import com.example.g2_se1630_swd392.entity.Permission;
import com.example.g2_se1630_swd392.entity.User;

import javax.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Trung Nguyễn Bá
 * @created 9/27/2023
 * @project IMS_G2_SWD392
 */

public interface UserService extends BaseService<User, Integer> {

    List<UserResponse> findAll();

    User getCurrentUser();

    String sendEmailCode(SendEmailCodeRequest request) throws MessagingException, UnsupportedEncodingException;

    String sendEmailCodeForRegister(SendEmailCodeRequest request) throws MessagingException, UnsupportedEncodingException;

    LoginResponse loginByUsernamePass(LoginRequest request);

    LoginResponse singleSignOnGoogle(GoogleLoginRequest request);

    UserResponse changeActive(Integer id);

    UserResponse registerByGoogle(GoogleLoginRequest request);

    UserResponse changePassword(Integer id, ChangePasswordRequest request);

    UserResponse registerUser(RegisterUserRequest request);

    UserResponse changePassOfForgotPass(ChangePasswordOfForgotPassRequest request);

    void sendSms(String toPhoneNumber);

}
