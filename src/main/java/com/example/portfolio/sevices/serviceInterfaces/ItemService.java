package com.example.portfolio.sevices.serviceInterfaces;

import com.example.portfolio.DTOs.item.ItemCreateRequest;
import com.example.portfolio.DTOs.item.ItemResponse;

public interface ItemService {
    ItemResponse get(Long id);
    ItemResponse create(ItemCreateRequest itemRequest);
    ItemResponse update(Long id, ItemCreateRequest itemRequest);
    void delete(Long id);

}
