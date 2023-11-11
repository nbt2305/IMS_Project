package com.example.g2_se1630_swd392.mapper;

import com.example.g2_se1630_swd392.dto.user.request.CreateUserRequest;
import com.example.g2_se1630_swd392.dto.user.request.GoogleLoginRequest;
import com.example.g2_se1630_swd392.dto.user.request.RegisterUserRequest;
import com.example.g2_se1630_swd392.dto.user.request.UpdateUserRequest;
import com.example.g2_se1630_swd392.dto.user.response.UserResponse;
import com.example.g2_se1630_swd392.entity.SystemSetting;
import com.example.g2_se1630_swd392.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
public class UserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public User convertCreateUserDtoToUser(CreateUserRequest request) {
        User response = new User();
        response.setUsername(request.getUsername());
        response.setEmail(request.getEmail());
        response.setPicture(request.getPicture());
        response.setName(request.getName());
        response.setEmailVerified(request.getEmailVerified());
        response.setRoleId(request.getRoleId());
        return response;
    }

    public User convertUpdateUserDtoToUser(UpdateUserRequest request, User oldUser) {
        User response = modelMapper.map(request, User.class);
        response.setId(oldUser.getId());
        return response;
    }

    public User convertGoogleLoginRequestToUser(GoogleLoginRequest request){
        User response = new User();
        response.setUsername(request.getUsername());
        response.setPassword(request.getPassword());
        response.setEmail(request.getEmail());
        response.setPicture(request.getPicture());
        response.setName(request.getName());
        response.setEmailVerified(request.getEmailVerified());
        response.setPhoneNumber(request.getPhoneNumber());
        response.setRoleId(request.getRoleId());
        return response;
//        return modelMapper.map(request, User.class);
    }

    public UserResponse convertUserToUserResponse(User request, SystemSetting role) {
        UserResponse response = modelMapper.map(request, UserResponse.class);
            response.setRole(role);
        return response;
    }

    public UserResponse convertUserToUserResponse(User request) {
        return modelMapper.map(request, UserResponse.class);
    }

    public User convertRegisterUserRequestToUser(RegisterUserRequest request){
        User response = new User();
        response.setName(request.getName());
        response.setEmail(request.getEmail());
        response.setRoleId(request.getRoleId());
        response.setPhoneNumber(request.getPhoneNumber());
        return response;
    }

}
