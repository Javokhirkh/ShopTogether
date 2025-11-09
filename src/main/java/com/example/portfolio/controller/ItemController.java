package com.example.portfolio.controller;

import com.example.portfolio.DTOs.item.ItemCreateRequest;
import com.example.portfolio.DTOs.item.ItemResponse;
import com.example.portfolio.sevices.serviceInterfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> get(@PathVariable Long id) {
        ItemResponse ItemResponse = itemService.get(id);
        return new ResponseEntity<>(ItemResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(@RequestBody ItemCreateRequest itemRequest) {
        ItemResponse itemResponse = itemService.create(itemRequest);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemResponse> update(@PathVariable Long id,@RequestBody ItemCreateRequest itemRequest) {
        ItemResponse response=itemService.update(id,itemRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return  ResponseEntity.noContent().build();
    }


}
