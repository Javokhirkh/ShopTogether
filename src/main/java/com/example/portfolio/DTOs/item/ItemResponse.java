package com.example.portfolio.DTOs.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponse {
    private Long id;
    private String name;
    private String category;
    private boolean isBought;
}