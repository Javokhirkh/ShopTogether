package com.example.portfolio.sevices.serviceInterfaces;

import com.example.portfolio.DTOs.user.UserRegisterRequest;
import com.example.portfolio.DTOs.user.UserResponse;

public interface UserService {
    UserResponse create(UserRegisterRequest user);
    UserResponse get(Long id);
    UserResponse update(Long id, UserRegisterRequest user);
    void delete(Long id);
}
