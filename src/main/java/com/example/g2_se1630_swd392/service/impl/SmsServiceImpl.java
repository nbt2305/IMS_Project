//package com.example.g2_se1630_swd392.service.impl;
//
//import com.example.g2_se1630_swd392.common.exception.RecordNotFoundException;
//import com.example.g2_se1630_swd392.entity.User;
//import com.example.g2_se1630_swd392.common.helper.GenerateString;
//import com.example.g2_se1630_swd392.repository.UserRepository;
//import com.example.g2_se1630_swd392.service.SmsService;
//import com.twilio.Twilio;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//
//@Service
//@RequiredArgsConstructor
//public class SmsServiceImpl implements SmsService {
//    private final UserRepository userRepository;
//
//    @Value("${twilio.accountSid}")
//    private String accountSid;
//
//    @Value("${twilio.authToken}")
//    private String authToken;
//
//    @Value("${twilio.phoneNumber}")
//    private String twilioPhoneNumber;
//    @Override
//    public void sendSms(String email) {
//        User foundUser = userRepository.findByEmailAndActiveTrue(email);
//        if (foundUser == null){
//            throw new RecordNotFoundException("User");
//        }
//        Twilio.init(accountSid, authToken);
//        String text = "Mã xác tực đăng nhập IMS của bạn là: [[code]]. Không chia sẻ với bất kỳ ai. Hãy nhập vào tài khoản IMS ngay bây giờ.";
//        text = text.replace("[[code]]", GenerateString.generateRandomCode(6));
//        Message message = Message.creator(
//                new PhoneNumber(foundUser.getPhoneNumber()),
//                new PhoneNumber(twilioPhoneNumber),
//                text
//        ).create();
//
//        System.out.println("Tin nhắn đã được gửi với ID: " + message.getSid());
//    }
//}
