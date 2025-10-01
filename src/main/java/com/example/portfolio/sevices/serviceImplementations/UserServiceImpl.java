package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.user.UserRegisterRequest;
import com.example.portfolio.DTOs.user.UserResponse;
import com.example.portfolio.models.User;
import com.example.portfolio.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.portfolio.sevices.serviceInterfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;


    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserResponse createUser(UserRegisterRequest dto) {
        return null;
    }


    @Override
    public UserResponse getUser(Long id) {
        return null;
    }

    @Override
    public UserResponse updateUser(Long id, UserRegisterRequest user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
