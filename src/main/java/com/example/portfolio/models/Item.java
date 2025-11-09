package com.example.portfolio.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;

    private int quantity;

    private boolean isBought = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    @ManyToOne
    @JoinColumn(name = "added_by")
    private User addedBy;

    private LocalDateTime createdAt;
}