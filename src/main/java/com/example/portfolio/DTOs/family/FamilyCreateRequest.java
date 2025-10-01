package com.example.portfolio.DTOs.family;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FamilyCreateRequest {
    @NotBlank
    private String name;
}