package com.example.ECommerce.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ECommerce.ECommerceApplication;

@SpringBootApplication
public class CorsConfig {
    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
}