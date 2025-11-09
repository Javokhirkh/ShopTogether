package com.example.portfolio.sevices.serviceInterfaces;


import com.example.portfolio.DTOs.shoppingList.ShoppingListRequest;
import com.example.portfolio.DTOs.shoppingList.ShoppingListResponse;

public interface ShoppingListService {
    ShoppingListResponse create(ShoppingListRequest shoppingListRequest);
    ShoppingListResponse get(Long id);
    ShoppingListResponse update(Long id, ShoppingListRequest shoppingListRequest);
    void delete(Long id);
    
}
