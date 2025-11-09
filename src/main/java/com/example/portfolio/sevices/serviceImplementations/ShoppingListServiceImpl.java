package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.shoppingList.ShoppingListRequest;
import com.example.portfolio.DTOs.shoppingList.ShoppingListResponse;
import com.example.portfolio.exceptions.ResourceNotFoundException;
import com.example.portfolio.models.ShoppingList;
import com.example.portfolio.repository.ShoppingListRepository;
import com.example.portfolio.sevices.serviceInterfaces.ShoppingListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepository repository;

    public ShoppingListServiceImpl(ShoppingListRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public ShoppingListResponse create(ShoppingListRequest shoppingListRequest) {
        ShoppingList list = mapToEntity(shoppingListRequest);
        list.setCreatedAt(LocalDateTime.now());
        ShoppingList saved = repository.save(list);
        return mapToDto(saved);
    }

    @Override
    public ShoppingListResponse get(Long id) {
        ShoppingList list = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping List not found with id: " + id));
        return mapToDto(list);
    }

    @Transactional
    @Override
    public ShoppingListResponse update(Long id, ShoppingListRequest shoppingListRequest) {
        ShoppingList list = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping List not found with id: " + id));

        if (shoppingListRequest.getName() != null && !shoppingListRequest.getName().isBlank()) {
            list.setName(shoppingListRequest.getName());
        }

        if (shoppingListRequest.getDescription() != null && !shoppingListRequest.getDescription().isBlank()) {
            list.setDescription(shoppingListRequest.getDescription());
        }

        list.setCompleted(shoppingListRequest.isCompleted());
        list.setUpdatedAt(LocalDateTime.now());

        ShoppingList updated = repository.save(list);
        return mapToDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Shopping List not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private ShoppingListResponse mapToDto(ShoppingList list) {
        return ShoppingListResponse.builder()
                .id(list.getId())
                .name(list.getName())
                .description(list.getDescription())
                .completed(list.isCompleted())
                .createdAt(list.getCreatedAt())
                .updatedAt(list.getUpdatedAt())
                .build();
    }

    private ShoppingList mapToEntity(ShoppingListRequest dto) {
        return ShoppingList.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .completed(dto.isCompleted())
                .build();
    }
}
