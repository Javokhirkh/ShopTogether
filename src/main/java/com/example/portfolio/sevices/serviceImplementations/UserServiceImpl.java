package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.user.UserRegisterRequest;
import com.example.portfolio.DTOs.user.UserResponse;
import com.example.portfolio.exceptions.ResourceNotFoundException;
import com.example.portfolio.models.User;
import com.example.portfolio.repository.UserRepository;
import com.example.portfolio.sevices.serviceInterfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public UserResponse create(UserRegisterRequest dto) {
        User user = mapToEntity(dto);
        User saved = repository.save(user);
        return mapToDto(saved);
    }

    @Override
    public UserResponse get(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToDto(user);
    }

    @Transactional
    @Override
    public UserResponse update(Long id, UserRegisterRequest dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword()); // optionally encode
        }

        User updated = repository.save(user);
        return mapToDto(updated);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private User mapToEntity(UserRegisterRequest dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    private UserResponse mapToDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
