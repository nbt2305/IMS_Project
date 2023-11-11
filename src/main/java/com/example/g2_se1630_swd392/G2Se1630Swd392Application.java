package com.example.g2_se1630_swd392;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class G2Se1630Swd392Application {

    public static void main(String[] args) {
        SpringApplication.run(G2Se1630Swd392Application.class, args);
    }

}
