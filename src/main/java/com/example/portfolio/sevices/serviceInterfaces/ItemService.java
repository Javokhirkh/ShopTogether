package com.example.portfolio.sevices.serviceInterfaces;

import com.example.portfolio.DTOs.item.ItemCreateRequest;
import com.example.portfolio.DTOs.item.ItemResponse;

public interface ItemService {
    ItemResponse getItem(Long id);
    ItemResponse CreateItem(ItemCreateRequest itemRequest);
    ItemResponse updateItem(Long id, ItemCreateRequest itemRequest);
    void deleteItem(Long id);

}
