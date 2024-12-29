package com.example.mountaineerback.service.impl;

import com.example.mountaineerback.model.request.ChangeRequest;
import com.example.mountaineerback.model.request.LoginRequest;
import com.example.mountaineerback.model.request.RegisterRequest;
import com.example.mountaineerback.model.dto.UserDTO;
import com.example.mountaineerback.model.entity.User;
import com.example.mountaineerback.repository.UserRepository;
import com.example.mountaineerback.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.mountaineerback.model.enums.USER_ROLE.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // 登入
    @Override
    public Optional<UserDTO> login(LoginRequest loginRequest) {
        Optional<User> optUser = userRepository.findByUsername(loginRequest.getUsername());
        // 判斷密碼
        if(optUser.isPresent() && optUser.get().getPassword().equals(loginRequest.getPassword())) {
            return Optional.of(modelMapper.map(optUser.get(), UserDTO.class));
        }
        return Optional.empty();
    }

    // 註冊
    @Override
    public Optional<UserDTO> register(RegisterRequest registerRequest) {

        // isEmpty:代表沒有重複   isPresent:代表有重複的用戶
        Optional<User> optUserEmail = userRepository.findByEmail(registerRequest.getEmail());
        Optional<User> optUserUserName = userRepository.findByUsername(registerRequest.getUsername());

        if(optUserEmail.isPresent()) {
            return Optional.of(modelMapper.map(optUserEmail.get(), UserDTO.class));
        }

        if (optUserUserName.isPresent()) {
            return Optional.of(modelMapper.map(optUserUserName.get(), UserDTO.class));
        }

        if(optUserUserName.isEmpty() && optUserEmail.isEmpty()) {
            User user = modelMapper.map(registerRequest, User.class);
            user.setRole(ROLE_GUEST); //預設值
            System.out.println("Before" + user);
            userRepository.save(user);
            System.out.println("After" + user);
            return Optional.of(modelMapper.map(user, UserDTO.class));
        }

        return Optional.empty();
    }

    //修改
    @Override
    public Optional<UserDTO> update(Long userId, ChangeRequest changeRequest) {
        Optional<User> optUser = userRepository.findById(userId);


        if(optUser.isPresent()) {

            UserDTO userDTO = modelMapper.map(optUser.get(), UserDTO.class);
            if (userDTO.getPassword().equals(changeRequest.getPassword())) {


                // 修改資料
                if (changeRequest.getNewPassword().isEmpty()) {

                }
                else {
                    userDTO.setPassword(changeRequest.getNewPassword());
                }

                userDTO.setTrueName(changeRequest.getTrueName());

                // 存入
                userRepository.save(modelMapper.map(userDTO, User.class));

                return Optional.of(userDTO);
            }
        }
       return Optional.empty();
    }

    // 取得
    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(order -> modelMapper.map(order, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO upgrade(Long id) {

        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isPresent()) {
            if(optUser.get().getRole().equals(ROLE_GUEST)) {
                optUser.get().setRole(ROLE_MEMBER);
            } else if (optUser.get().getRole().equals(ROLE_MEMBER)) {
                optUser.get().setRole(ROLE_ADMIN);
            }
            userRepository.save(optUser.get());
        }
        return modelMapper.map(optUser.orElse(null), UserDTO.class);
    }
}
