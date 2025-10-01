package com.example.portfolio.sevices.serviceInterfaces;


import com.example.portfolio.DTOs.shoppingList.ShoppingListResponse;
import com.example.portfolio.models.ShoppingList;

public interface ShoppingListService {
    ShoppingListResponse createShoppingList(ShoppingList  shoppingList);
    ShoppingListResponse getShoppingList(Long id);
    ShoppingListResponse updateShoppingList(Long id,ShoppingList shoppingList);
    void deleteShoppingList(Long id);
    
}
