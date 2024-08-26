package com.example.UserService.Security.models;

import com.example.UserService.Models.Roles;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    private Roles role;
    public CustomGrantedAuthority(Roles role){
        this.role = role;
    }
    @Override
    public String getAuthority() {
        return role.getRoles();
    }
}
