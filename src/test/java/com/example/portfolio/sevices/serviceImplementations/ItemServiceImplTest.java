package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.item.ItemCreateRequest;
import com.example.portfolio.DTOs.item.ItemResponse;
import com.example.portfolio.exceptions.ResourceNotFoundException;
import com.example.portfolio.models.Item;
import com.example.portfolio.repository.ItemRepository;
import com.example.portfolio.sevices.serviceImplementations.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServiceImplTest {

    private ItemRepository repository;
    private ItemServiceImpl service;

    @BeforeEach
    void init() {
        repository = Mockito.mock(ItemRepository.class);
        service = new ItemServiceImpl(repository);
    }

    @Test
    void testGet_Success() {
        Item item = Item.builder()
                .id(1L)
                .name("Milk")
                .category("Food")
                .isBought(false)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(item));

        ItemResponse response = service.get(1L);

        assertEquals(1L, response.getId());
        assertEquals("Milk", response.getName());
        assertEquals("Food", response.getCategory());
        assertFalse(response.isBought());
    }

    @Test
    void testGet_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.get(1L));
    }

    @Test
    void testCreate() {
        ItemCreateRequest req = new ItemCreateRequest();
        req.setName("Bread");
        req.setCategory("Food");
        req.setBought(false);

        Item saved = Item.builder()
                .id(10L)
                .name(req.getName())
                .category(req.getCategory())
                .isBought(req.isBought())
                .build();

        when(repository.save(any(Item.class))).thenReturn(saved);

        ItemResponse response = service.create(req);

        assertEquals("Bread", response.getName());
        assertEquals("Food", response.getCategory());
        assertFalse(response.isBought());
    }

    @Test
    void testUpdate_Success() {
        Item item = Item.builder()
                .id(1L)
                .name("Apple")
                .category("Fruit")
                .isBought(false)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(item));

        ItemCreateRequest req = new ItemCreateRequest();
        req.setName("Green Apple");
        req.setCategory("Fruit");
        req.setBought(true);

        ItemResponse response = service.update(1L, req);

        assertEquals("Green Apple", response.getName());
        assertEquals("Fruit", response.getCategory());
        assertTrue(response.isBought());
    }

    @Test
    void testUpdate_NotFound() {
        ItemCreateRequest req = new ItemCreateRequest();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, req));
    }

    @Test
    void testDelete() {
        service.delete(5L);
        verify(repository, times(1)).deleteById(5L);
    }
}
