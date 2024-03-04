package com.blogapplication.blogapplication.service;

import com.blogapplication.blogapplication.entities.User;
import com.blogapplication.blogapplication.payload.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    UserDto registerNewUser(UserDto user);
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    String deleteUser(Integer userId);
}
