package com.example.portfolio.DTOs.shoppingList;

import com.example.portfolio.models.Family;
import com.example.portfolio.models.Item;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ShoppingListRequest {
    private String name;
    private Long familyId;
    private String description;
    private boolean completed;
    private Long createdBy;
}
