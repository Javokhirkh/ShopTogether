package com.example.portfolio.DTOs.item;

import lombok.Data;

@Data
public class ItemResponse {
    private Long id;
    private String name;
    private String category;
    private boolean bought;
}