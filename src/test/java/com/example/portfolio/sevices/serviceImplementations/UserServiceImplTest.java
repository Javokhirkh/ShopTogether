package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.user.UserRegisterRequest;
import com.example.portfolio.DTOs.user.UserResponse;
import com.example.portfolio.exceptions.ResourceNotFoundException;
import com.example.portfolio.models.User;
import com.example.portfolio.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserRepository repository;
    private UserServiceImpl service;

    @BeforeEach
    void init() {
        repository = Mockito.mock(UserRepository.class);
        service = new UserServiceImpl(repository);
    }

    @Test
    void testCreate() {
        UserRegisterRequest req = new UserRegisterRequest();
        req.setUsername("john");
        req.setEmail("john@mail.com");
        req.setPassword("123");

        User saved = User.builder()
                .id(1L)
                .username("john")
                .email("john@mail.com")
                .password("123")
                .build();

        when(repository.save(any(User.class))).thenReturn(saved);

        UserResponse response = service.create(req);

        assertEquals("john", response.getUsername());
        assertEquals("john@mail.com", response.getEmail());
    }

    @Test
    void testGet_Success() {
        User user = User.builder()
                .id(1L)
                .username("alice")
                .email("alice@mail.com")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = service.get(1L);

        assertEquals("alice", response.getUsername());
        assertEquals("alice@mail.com", response.getEmail());
    }

    @Test
    void testGet_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.get(1L));
    }

    @Test
    void testUpdate_Success() {
        User user = User.builder()
                .id(1L)
                .username("old")
                .email("old@mail.com")
                .password("oldpass")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(user));

        UserRegisterRequest req = new UserRegisterRequest();
        req.setUsername("new");
        req.setEmail("new@mail.com");
        req.setPassword("newpass");

        UserResponse response = service.update(1L, req);

        assertEquals("new", response.getUsername());
        assertEquals("new@mail.com", response.getEmail());
    }

    @Test
    void testUpdate_NotFound() {
        UserRegisterRequest req = new UserRegisterRequest();
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

