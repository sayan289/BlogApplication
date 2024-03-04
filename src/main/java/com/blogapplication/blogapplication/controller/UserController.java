package com.blogapplication.blogapplication.controller;

import com.blogapplication.blogapplication.payload.UserDto;
import com.blogapplication.blogapplication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@EnableMethodSecurity(prePostEnabled = true)
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/add")
    public ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId)
    {
        UserDto updateuser = this.userService.updateUser(userDto,userId);
        return ResponseEntity.ok(updateuser);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteuser(@PathVariable Integer userId)
    {
        String delete = userService.deleteUser(userId);
        return new ResponseEntity<>(delete,HttpStatus.OK);
    }
    @GetMapping("/get")
    public ResponseEntity<List<UserDto>> getuser()
    {
        List<UserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }
    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDto> getuserbyid(@PathVariable Integer userId)
    {
        UserDto userById = userService.getUserById(userId);
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }
}
