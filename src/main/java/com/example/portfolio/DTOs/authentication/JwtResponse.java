package com.example.portfolio.DTOs.authentication;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";  // 👈 why this?
}