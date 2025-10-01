package com.example.portfolio.DTOs.shoppingList;

import com.example.portfolio.models.Family;
import com.example.portfolio.models.Item;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class ShoppingListResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private Long familyId;
    private String description;
    private boolean completed;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Item> items = new HashSet<>();
    private Family family;

}
