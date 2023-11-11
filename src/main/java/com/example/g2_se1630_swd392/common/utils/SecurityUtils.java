//package com.example.g2_se1630_swd392.common.utils;
//
//import com.example.g2_se1630_swd392.common.exception.RecordNotFoundException;
//import com.example.g2_se1630_swd392.entity.User;
//import com.example.g2_se1630_swd392.repository.UserRepository;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SecurityUtils {
//
//    private final UserRepository userRepository;
//
//    public SecurityUtils(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
////    public String getCurrentRole(String token) {
////        String secret = "mySecretKey";
////        Claims claims = Jwts.parser()
////                .setSigningKey(secret)
////                .parseClaimsJws(token)
////                .getBody();
////        return (String) claims.get("role");
////    }
////
////    public User getCurrentLoginUserAccount(String token) {
////        String secret = "mySecretKey";
////        Claims claims = Jwts.parser()
////                .setSigningKey(secret)
////                .parseClaimsJws(token)
////                .getBody();
////        String email = (String) claims.get("email");
////        return userRepository.findByEmailAndActiveTrue(email);
////    }
//
//    public User getCurrentLoginUserAccount() {
//        User userJwtPayload = getCurrentUserLogin();
//        if (userJwtPayload.getId() == null) {
//            throw new RecordNotFoundException("NOT FOUND");
//        }
//
//        return userJwtPayload;
//    }
//
//    public User getCurrentUserLogin() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        return extractPrincipal(securityContext.getAuthentication());
//    }
//
//    public User extractPrincipal(Authentication authentication) {
//        if (authentication == null) {
//            return null;
//        } else if (authentication.getPrincipal() instanceof UserDetails) {
//            System.out.println(authentication.getPrincipal());
//            return userRepository.findByEmailAndActiveTrue(((UserDetails) authentication.getPrincipal()).getUsername());
//        }
//        return null;
//    }
//
//
//}
