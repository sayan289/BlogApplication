package com.blogapplication.blogapplication.payload;

import com.blogapplication.blogapplication.entities.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotBlank(message = "Name is required")
    private String name;
    @Email
    private String email;
    @NotBlank
    @Size(min=4,message = "Password must be more than 3 character")
    private String password;
    @NotBlank(message = "about is required")
    private String about;
    private Set<RoleDto> roles=new HashSet<>();
}
