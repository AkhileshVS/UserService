package com.example.UserService.Security.service;

import com.example.UserService.Models.Users;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.Security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByEmail(username);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("User with email %s not found".format(username));
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(user.get());
        return customUserDetails;
    }
}
