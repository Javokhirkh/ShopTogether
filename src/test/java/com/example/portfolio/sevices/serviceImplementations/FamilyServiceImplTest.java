package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.family.FamilyCreateRequest;
import com.example.portfolio.DTOs.family.FamilyResponse;
import com.example.portfolio.exceptions.ResourceNotFoundException;
import com.example.portfolio.models.Family;
import com.example.portfolio.repository.FamilyRepository;
import com.example.portfolio.sevices.serviceImplementations.FamilyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FamilyServiceImplTest {

    private FamilyRepository repository;
    private FamilyServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(FamilyRepository.class);
        service = new FamilyServiceImpl(repository);
    }

    @Test
    void create_success() {
        FamilyCreateRequest request = new FamilyCreateRequest();
        request.setName("My Family");

        Family saved = Family.builder()
                .id(1L)
                .name("My Family")
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.save(any(Family.class))).thenReturn(saved);

        FamilyResponse response = service.create(request);

        assertEquals(1L, response.getId());
        assertEquals("My Family", response.getName());
        verify(repository).save(any(Family.class));
    }

    @Test
    void get_success() {
        Family family = Family.builder()
                .id(1L)
                .name("Family A")
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(family));

        FamilyResponse response = service.get(1L);

        assertEquals(1L, response.getId());
        assertEquals("Family A", response.getName());
    }

    @Test
    void get_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.get(1L));
    }

    @Test
    void update_success() {
        Family existing = Family.builder()
                .id(1L)
                .name("Old Name")
                .createdAt(LocalDateTime.now())
                .build();

        FamilyCreateRequest request = new FamilyCreateRequest();
        request.setName("New Name");

        Family updated = Family.builder()
                .id(1L)
                .name("New Name")
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(updated);

        FamilyResponse response = service.update(1L, request);

        assertEquals("New Name", response.getName());
        verify(repository).save(existing);
    }

    @Test
    void update_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> service.update(1L, new FamilyCreateRequest()));
    }

    @Test
    void delete_success() {
        service.delete(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void getFamilyByUserId_success() {
        Family family = Family.builder()
                .id(10L)
                .name("Smith Family")
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.findByUserId(5L)).thenReturn(Optional.of(family));

        FamilyResponse response = service.getFamilyByUserId(5L);

        assertEquals(10L, response.getId());
        assertEquals("Smith Family", response.getName());
    }

    @Test
    void getFamilyByUserId_notFound() {
        when(repository.findByUserId(5L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> service.getFamilyByUserId(5L));
    }
}
