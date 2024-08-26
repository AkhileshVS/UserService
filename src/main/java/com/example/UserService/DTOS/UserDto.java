package com.example.UserService.DTOS;

import com.example.UserService.Models.Roles;
import com.example.UserService.Models.Users;
import jakarta.persistence.ManyToMany;

import java.util.List;

public class UserDto {
    private String name;

    private String email;
    @ManyToMany
    private List<Roles> roles;
    private boolean isEmailVerified;
    public static UserDto from(Users user){
        if(user==null){
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.email = user.getEmail();
        userDto.name = user.getName();
        userDto.roles = user.getRole();
        userDto.isEmailVerified = user.isEmailVerified();

        return userDto;
    }
}
