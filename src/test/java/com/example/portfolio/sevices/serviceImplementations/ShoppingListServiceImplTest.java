package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.shoppingList.ShoppingListRequest;
import com.example.portfolio.DTOs.shoppingList.ShoppingListResponse;
import com.example.portfolio.exceptions.ResourceNotFoundException;
import com.example.portfolio.models.ShoppingList;
import com.example.portfolio.repository.ShoppingListRepository;
import com.example.portfolio.sevices.serviceImplementations.ShoppingListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShoppingListServiceImplTest {

    private ShoppingListRepository repository;
    private ShoppingListServiceImpl service;

    @BeforeEach
    void init() {
        repository = Mockito.mock(ShoppingListRepository.class);
        service = new ShoppingListServiceImpl(repository);
    }

    @Test
    void testCreate() {
        ShoppingListRequest req = new ShoppingListRequest();
        req.setName("Groceries");
        req.setDescription("Weekly items");
        req.setCompleted(false);

        ShoppingList saved = ShoppingList.builder()
                .id(1L)
                .name("Groceries")
                .description("Weekly items")
                .completed(false)
                .build();

        when(repository.save(any(ShoppingList.class))).thenReturn(saved);

        ShoppingListResponse response = service.create(req);

        assertEquals("Groceries", response.getName());
        assertEquals("Weekly items", response.getDescription());
        assertFalse(response.isCompleted());
    }

    @Test
    void testGet_Success() {
        ShoppingList list = ShoppingList.builder()
                .id(1L)
                .name("Groceries")
                .description("List")
                .completed(false)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(list));

        ShoppingListResponse response = service.get(1L);

        assertEquals(1L, response.getId());
        assertEquals("Groceries", response.getName());
    }

    @Test
    void testGet_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.get(1L));
    }

    @Test
    void testUpdate_Success() {
        ShoppingList list = ShoppingList.builder()
                .id(1L)
                .name("Old name")
                .description("Old description")
                .completed(false)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(list));

        ShoppingListRequest req = new ShoppingListRequest();
        req.setName("New name");
        req.setDescription("New desc");
        req.setCompleted(true);

        ShoppingListResponse response = service.update(1L, req);

        assertEquals("New name", response.getName());
        assertEquals("New desc", response.getDescription());
        assertTrue(response.isCompleted());
    }

    @Test
    void testUpdate_NotFound() {
        ShoppingListRequest req = new ShoppingListRequest();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, req));
    }

    @Test
    void testDelete_Success() {
        when(repository.existsById(5L)).thenReturn(true);
        service.delete(5L);
        verify(repository, times(1)).deleteById(5L);
    }

    @Test
    void testDelete_NotFound() {
        when(repository.existsById(5L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(5L));
    }
}
