package com.example.portfolio.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "families")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;




    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FamilyMember> members = new HashSet<>();

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShoppingList> shoppingLists = new HashSet<>();
}
