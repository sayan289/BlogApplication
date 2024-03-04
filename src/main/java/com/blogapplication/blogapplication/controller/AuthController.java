package com.blogapplication.blogapplication.controller;

import com.blogapplication.blogapplication.payload.UserDto;
import com.blogapplication.blogapplication.security.JwtRequest;
import com.blogapplication.blogapplication.security.JwtResponse;
import com.blogapplication.blogapplication.security.JwtTokenHelper;
import com.blogapplication.blogapplication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;


//    @Autowired
//    private AuthenticationManager manager;

//    At first when /auth/loginin api is hit it will called doAuthenticate method which will authenticate
//     the user with respect to username and password by using daoAuthentication provider. If the user
//    is not present in the database it will throws an exception and show bad credential, if everything
//    is ok then it authenticate the user, that means the user present in the database. Then by loadbyusername method
//    we load the user details of that particular email and store it in userdetails. Then generate token
//    for that particular user and in the JwtResponse set the username and the password.
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;


    @Autowired
    private JwtTokenHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .useername(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            daoAuthenticationProvider.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
    {
        UserDto registeredUser = userService.registerNewUser(userDto);
        return  new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
    }

}
