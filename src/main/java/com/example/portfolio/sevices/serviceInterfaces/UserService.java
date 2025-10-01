package com.example.portfolio.sevices.serviceInterfaces;

import com.example.portfolio.DTOs.user.UserRegisterRequest;
import com.example.portfolio.DTOs.user.UserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserResponse createUser(UserRegisterRequest user);
    UserResponse getUser(Long id);
    UserResponse updateUser(Long id,UserRegisterRequest user);
    void deleteUser(Long id);
}
