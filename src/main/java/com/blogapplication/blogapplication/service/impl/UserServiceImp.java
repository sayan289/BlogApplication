package com.blogapplication.blogapplication.service.impl;

import com.blogapplication.blogapplication.Repository.RoleRepo;
import com.blogapplication.blogapplication.Repository.UserRepo;
import com.blogapplication.blogapplication.config.AppConstant;
import com.blogapplication.blogapplication.entities.Role;
import com.blogapplication.blogapplication.entities.User;
import com.blogapplication.blogapplication.exception.ResourceNotFoundException;
import com.blogapplication.blogapplication.payload.UserDto;
import com.blogapplication.blogapplication.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        //encode password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        //roles
        Role role = this.roleRepo.findById(AppConstant.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser,UserDto.class);
    }

    //    create User
    @Override
    public UserDto createUser(UserDto userDto) {
        User saveUser=this.dtoToUser(userDto);
        this.userRepo.save(saveUser);
        return this.userToDto(saveUser);
    }
//    update User
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," id ",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updateUser=this.userRepo.save(user);
        return this.userToDto(updateUser);
    }
//    get a particular user with respect to userId
    @Override
    public UserDto getUserById(Integer userId) {
        User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," id ",userId));
        return this.userToDto(user);
    }
//    Get All user
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public String deleteUser(Integer userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," id ",userId));
        this.userRepo.delete(user);
        return "Deleted";

    }
//    To convert User to UserDto
    public User dtoToUser(UserDto userDto)
    {
        User user=this.modelMapper.map(userDto,User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }
//    To convert UserDto to User
    public UserDto userToDto(User user)
    {
        UserDto userDto=this.modelMapper.map(user,UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
//        userDto.setPassword(user.getPassword());
        return  userDto;
    }
}
