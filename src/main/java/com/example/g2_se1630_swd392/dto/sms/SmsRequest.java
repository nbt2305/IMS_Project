package com.example.g2_se1630_swd392.dto.sms;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SmsRequest {
    private String email;
    private String message;
}
