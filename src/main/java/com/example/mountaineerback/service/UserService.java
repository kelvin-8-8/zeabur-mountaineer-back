package com.example.mountaineerback.service;

import com.example.mountaineerback.model.request.ChangeRequest;
import com.example.mountaineerback.model.request.LoginRequest;
import com.example.mountaineerback.model.request.RegisterRequest;
import com.example.mountaineerback.model.dto.UserDTO;


import java.util.Optional;

public interface UserService {
//    Optional<UserDTO> findByUsername(String username);
    Optional<UserDTO> login(LoginRequest loginRequest);
    Optional<UserDTO> register(RegisterRequest registerRequest);

    Optional<UserDTO> update(Long userId, ChangeRequest changeRequest);

    // 用戶

}
