package com.example.UserService.Repository;

import com.example.UserService.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Users save(Users user);
    public Optional<Users> findByEmail(String email);
}

