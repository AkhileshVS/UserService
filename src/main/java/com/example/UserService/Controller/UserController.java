package com.example.UserService.Controller;

import com.example.UserService.DTOS.LogOutRequestDto;
import com.example.UserService.DTOS.LoginRequestDto;
import com.example.UserService.DTOS.SignUpRequestDto;
import com.example.UserService.DTOS.UserDto;
import com.example.UserService.Models.Token;
import com.example.UserService.Models.Users;
import com.example.UserService.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
private UserService userService;
public UserController(UserService userService){
    this.userService = userService;
}

@PostMapping("/signup")
    public Users Signup(@RequestBody SignUpRequestDto request){
        String email = request.getEmail();
        String password = request.getPassword();
        String name = request.getName();

        return userService.signUp(name,email,password);
    }

//    @PostMapping("/login")
//    public Token login(@RequestBody LoginRequestDto request){
//        String email = request.getEmail();
//        String password = request.getPassword();
//        return userService.login(email,password);
//    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogOutRequestDto request){
    String token = request.getToken();
    userService.logout(token);
    return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") String token){
    Users u = new Users();
    u = userService.validateToken(token);
    return UserDto.from(u);
    }
    @GetMapping("/hi")
    public String hi(){
    return "Hi";
    }
}
