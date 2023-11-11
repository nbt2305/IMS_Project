package com.example.g2_se1630_swd392.service.impl;

import com.example.g2_se1630_swd392.common.exception.*;
import com.example.g2_se1630_swd392.dto.user.request.*;
import com.example.g2_se1630_swd392.entity.Permission;
import com.example.g2_se1630_swd392.entity.SystemSetting;
import com.example.g2_se1630_swd392.common.helper.GenerateString;
import com.example.g2_se1630_swd392.repository.PermissionRepository;
import com.example.g2_se1630_swd392.repository.SystemSettingRepository;
import com.example.g2_se1630_swd392.security.config.PasswordEncoderImpl;
import com.example.g2_se1630_swd392.security.jwt.JwtUserDetailsService;
import com.example.g2_se1630_swd392.security.jwt.JwtUtil;
import com.example.g2_se1630_swd392.dto.user.response.LoginResponse;
import com.example.g2_se1630_swd392.dto.user.response.UserResponse;
import com.example.g2_se1630_swd392.entity.User;
import com.example.g2_se1630_swd392.mapper.UserMapper;
import com.example.g2_se1630_swd392.repository.UserRepository;
import com.example.g2_se1630_swd392.service.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.example.g2_se1630_swd392.common.utils.Constants;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SystemSettingRepository systemSettingRepository;
    private final UserMapper userMapper;
    private final JavaMailSender mailSender;
    private final JwtUtil jwtUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final PasswordEncoderImpl passwordEncoder;
    private final PermissionRepository permissionRepository;

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;


    @Override
    public UserResponse create(Object objectRequest) {
        var request = (CreateUserRequest) objectRequest;
        // Handle logic
        var foundUser = userRepository.findByEmailAndActiveTrue(request.getEmail());
        if (foundUser != null) {
            throw new NameAlreadyExistsException("Email");
        }
        var foundUserByUsername = userRepository.findByUsernameAndActiveTrue(request.getUsername());
        if (foundUserByUsername.isPresent()) {
            throw new NameAlreadyExistsException("Username");
        }
        String rawPass = request.getPassword();
        request.setPassword(passwordEncoder.encode(rawPass));
        // Mapper
        var user = userMapper.convertCreateUserDtoToUser(request);
        userRepository.save(user);
        var role = findRoleByUser(user);
        return userMapper.convertUserToUserResponse(user, role);
    }

    @Override
    @Transactional
    public UserResponse update(Integer id, Object objectRequest) {
        // Check current user
        var currentUser = getCurrentUser();
        if (!(Objects.equals(currentUser.getId(), id))) {
            throw new UnauthorizedException("Permission denied!");
        }
        var request = (UpdateUserRequest) objectRequest;
        // Handle logic
        var foundUser = findUserById(id);
        if (!Objects.equals(currentUser.getId(), foundUser.getId())) {
            throw new PermissionDeniedException();
        }
        // Mapper
        var user = userMapper.convertUpdateUserDtoToUser(request, foundUser);
        var role = findRoleByUser(user);
        return userMapper.convertUserToUserResponse(user, role);
    }

    @Override
    public UserResponse getDetail(Integer id) {
        var foundUser = findUserById(id);
        var role = findRoleByUser(foundUser);
        return userMapper.convertUserToUserResponse(findUserById(id), role);
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(findUserById(id));
    }

    @Override
    public List<UserResponse> findAll() {
        List<UserResponse> responses = new ArrayList<>();
        var users = userRepository.findAll();
        for (User user : users) {
            var role = findRoleByUser(user);
            responses.add(userMapper.convertUserToUserResponse(user, role));
        }
        return responses;
    }

    //    @Override
//    public User getCurrentUser() {
//        return getCurrentLoginUserAccount();
//    }
    @Override
    public User getCurrentUser() {
        User userJwtPayload = getCurrentUserLogin();
        if (userJwtPayload.getId() == null) {
            throw new RecordNotFoundException("NOT FOUND");
        }

        return userJwtPayload;
    }

    public User getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return extractPrincipal(securityContext.getAuthentication());
    }

    public User extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            System.out.println(authentication.getPrincipal());
            return userRepository.findByEmailAndActiveTrue(((UserDetails) authentication.getPrincipal()).getUsername());
        }
        return null;
    }

    @Override
    public String sendEmailCode(SendEmailCodeRequest request) throws MessagingException, UnsupportedEncodingException {
        User foundUser = userRepository.findByEmailAndActiveTrue(request.getEmail());
        if (foundUser != null) {
            return emailContent(foundUser);
        }
        throw new RecordNotFoundException("User");
    }

    @Override
    public String sendEmailCodeForRegister(SendEmailCodeRequest request) throws MessagingException, UnsupportedEncodingException {
        return emailContentForRegister(request.getEmail(), request.getName());
    }

    @Override
    public LoginResponse loginByUsernamePass(LoginRequest request) {
        User foundUser = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new RecordNotFoundException("Email")
        );
        if (!foundUser.getActive()) {
            throw new ConflictException("Your account is locked!");
        }
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(foundUser.getEmail());
        String passRequest = request.getPassword();
        if (!passwordEncoder.matches(passRequest, foundUser.getPassword())) {
            throw new UnauthorizedException("Username or password is incorrect!");
        }
        LoginResponse response = new LoginResponse();
        var role = getRoleNameOfUser(foundUser);
        var permissions = getPermissionsByUser(foundUser);
        response.setUser(foundUser);
        response.setToken(jwtUtil.generateToken(userDetails));
        response.setRole(role);
        response.setPermissions(permissions);
        return response;
    }

    @Override
    @Transactional
    public LoginResponse singleSignOnGoogle(GoogleLoginRequest request) {
        LoginResponse response = new LoginResponse();
        User foundUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (foundUser == null) {
            User user = userMapper.convertGoogleLoginRequestToUser(request);
            userRepository.save(user);
            var role = getRoleNameOfUser(user);
            var permissions = getPermissionsByUser(user);
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getEmail());
            response.setUser(user);
            response.setToken(jwtUtil.generateToken(userDetails));
            response.setRole(role);
            response.setPermissions(permissions);
            return response;
        }
        if (!foundUser.getActive()) {
            throw new ConflictException("Your account is locked!");
        }
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(foundUser.getEmail());
        var role = getRoleNameOfUser(foundUser);
        var permissions = getPermissionsByUser(foundUser);
        response.setUser(foundUser);
        response.setToken(jwtUtil.generateToken(userDetails));
        response.setRole(role);
        response.setPermissions(permissions);
        return response;
    }

    @Override
    public UserResponse changeActive(Integer id) {
        var foundUser = userRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("User")
        );
        var active = foundUser.getActive();
        foundUser.setActive(!active);
        userRepository.save(foundUser);
        return userMapper.convertUserToUserResponse(foundUser);
    }

    @Override
    public UserResponse registerByGoogle(GoogleLoginRequest request) {
        return null;
    }

    @Override
    public UserResponse changePassword(Integer id, ChangePasswordRequest request) {
        User foundUser = findUserById(id);
        if (!passwordEncoder.matches(request.getOldPassword(), foundUser.getPassword())) {
            throw new ConflictException("Old password is not match!");
        }
        // TODO: 10/11/2023 get length of password from config
        if (request.getNewPassword().length() < 8) {
            throw new ConflictException("Old password is not match!");
        }
        foundUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        return userMapper.convertUserToUserResponse(foundUser);
    }

    @Override
    public UserResponse registerUser(RegisterUserRequest request) {
        var domain = false;
        var systemSettings = systemSettingRepository.findAllByType(Constants.SystemConfig.EMAIL)
                .stream().map(s -> s.getName()).toList();
        for (String name : systemSettings) {
            if (request.getEmail().contains(name)) {
                domain = true;
                break;
            }
        }
        if (!domain) {
            throw new ConflictException("Email domain is incorrect!");
        }

        var foundUser = userRepository.findByEmailAndActiveTrue(request.getEmail());
        if (foundUser != null) {
            throw new NameAlreadyExistsException("Email");
        }
        if (request.getPassword().length() < 8) {
            throw new ConflictException("Length of password must be >= 8!");
        }
        var saveUser = userMapper.convertRegisterUserRequestToUser(request);
        saveUser.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(saveUser);
        return userMapper.convertUserToUserResponse(saveUser);
    }

    @Override
    public UserResponse changePassOfForgotPass(ChangePasswordOfForgotPassRequest request) {
        var foundUser = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new RecordNotFoundException("Email")
        );
        if (request.getPassword().length() < 8) {
            throw new ConflictException("Length of password must be >= 8!");
        }
        foundUser.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(foundUser);
        return userMapper.convertUserToUserResponse(foundUser);
    }

    @Override
    public void sendSms(String email) {
        User foundUser = userRepository.findByEmailAndActiveTrue(email);
        if (foundUser == null) {
            throw new RecordNotFoundException("User");
        }
        Twilio.init(accountSid, authToken);
        String text = "Mã xác tực đăng nhập IMS của bạn là: [[code]]. Không chia sẻ với bất kỳ ai. Hãy nhập vào tài khoản IMS ngay bây giờ.";
        text = text.replace("[[code]]", GenerateString.generateRandomCode(6));
        Message message = Message.creator(
                new PhoneNumber(foundUser.getPhoneNumber()),
                new PhoneNumber(twilioPhoneNumber),
                text
        ).create();

        System.out.println("Tin nhắn đã được gửi với ID: " + message.getSid());
    }

    private List<String> getPermissionsByUser(User user) {
        return permissionRepository.getAllByRoleId(user.getRoleId()).stream().map(Permission::getName).toList();
    }

    private String getRoleNameOfUser(User user) {
        return systemSettingRepository.findRoleNameById(user.getRoleId());
    }


    public String emailContent(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = Constants.SendMail.FROM_ADDRESS;
        String senderName = Constants.SendMail.SENDER_NAME;
        String subject = Constants.SendMail.SUBJECT;
        StringBuilder content = new StringBuilder();
        content.append(Constants.SendMail.DEAR);
        content.append(Constants.SendMail.MAIN_CONTENT);
        content.append(Constants.SendMail.CODE);
        content.append(Constants.SendMail.THANK_YOU);
        content.append(Constants.SendMail.COMPANY);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String contentResponse = content.toString();
        contentResponse = contentResponse.replace("[[name]]", user.getName());
        String code = GenerateString.generateRandomCode(6);
        contentResponse = contentResponse.replace("[[code]]", code);
        helper.setText(contentResponse, true);
        mailSender.send(message);
        return code;
    }

    public String emailContentForRegister(String email, String name) throws MessagingException, UnsupportedEncodingException {
        String toAddress = email;
        String fromAddress = Constants.SendMail.FROM_ADDRESS;
        String senderName = Constants.SendMail.SENDER_NAME;
        String subject = Constants.SendMail.SUBJECT;
        StringBuilder content = new StringBuilder();
        content.append(Constants.SendMail.DEAR);
        content.append(Constants.SendMail.MAIN_CONTENT);
        content.append(Constants.SendMail.CODE);
        content.append(Constants.SendMail.THANK_YOU);
        content.append(Constants.SendMail.COMPANY);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String contentResponse = content.toString();
        contentResponse = contentResponse.replace("[[name]]", name);
        String code = GenerateString.generateRandomCode(6);
        contentResponse = contentResponse.replace("[[code]]", code);
        helper.setText(contentResponse, true);
        mailSender.send(message);
        return code;
    }

    public User findUserById(Integer id) {
        return userRepository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new RecordNotFoundException("User")
        );
    }

    public User findUserByUsername(String email) {
        User foundUser = userRepository.findByEmailAndActiveTrue(email);
        if (foundUser != null) {
            throw new NameAlreadyExistsException(email);
        }
        return null;
    }

    public SystemSetting findRoleByUser(User user) {
        return systemSettingRepository.findById(user.getRoleId()).orElseThrow(
                () -> new RecordNotFoundException("" + user.getRoleId())
        );
    }


}
