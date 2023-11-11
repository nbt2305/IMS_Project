package com.example.g2_se1630_swd392.common.helper;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateString {
    public static String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            randomNumber.append(digit);
        }

        return randomNumber.toString();
    }
}
