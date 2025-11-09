package com.example.portfolio.controller;

import com.example.portfolio.DTOs.shoppingList.ShoppingListRequest;
import com.example.portfolio.DTOs.shoppingList.ShoppingListResponse;
import com.example.portfolio.sevices.serviceInterfaces.ShoppingListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopping-lists")
public class ShoppingListController {


    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping
    public ResponseEntity<ShoppingListResponse> create(@RequestBody ShoppingListRequest request) {
        ShoppingListResponse response = shoppingListService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingListResponse> get(@PathVariable Long id) {
        ShoppingListResponse response = shoppingListService.get(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingListResponse> update(
            @PathVariable Long id,
            @RequestBody ShoppingListRequest request
    ) {
        ShoppingListResponse response = shoppingListService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        shoppingListService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
