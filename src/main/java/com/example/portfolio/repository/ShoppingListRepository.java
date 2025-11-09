package com.example.portfolio.repository;

import com.example.portfolio.models.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListRepository extends JpaRepository<ShoppingList,Long> {
}
