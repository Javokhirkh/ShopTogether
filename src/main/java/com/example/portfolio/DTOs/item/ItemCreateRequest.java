package com.example.portfolio.DTOs.item;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemCreateRequest {
    @NotBlank
    private String name;
    private String category;
    private boolean bought ;
}