package com.example.g2_se1630_swd392.security.jwt;

import com.example.g2_se1630_swd392.common.exception.RecordNotFoundException;
//import com.example.g2_se1630_swd392.repository.RoleRepository;
import com.example.g2_se1630_swd392.repository.SystemSettingRepository;
import com.example.g2_se1630_swd392.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${spring.security.jwt.secret}")
    private String secret;

    @Value("${spring.security.jwt.expirationMs}")
    private long expirationMs;

    private final UserRepository userRepository;
    private final SystemSettingRepository systemSettingRepository;


    public JwtUtil(UserRepository userRepository, SystemSettingRepository systemSettingRepository) {
        this.userRepository = userRepository;
        this.systemSettingRepository = systemSettingRepository;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        var user = userRepository.findByEmailAndActiveTrue(userDetails.getUsername());
        var role = systemSettingRepository.findById(user.getRoleId()).orElseThrow(
                () -> new RecordNotFoundException("Role not found!")
        ).getName();
        claims.put("username", user.getEmail());
        claims.put("name", user.getName());
        claims.put("role", role);
        return createToken(claims, userDetails.getUsername());
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


}
