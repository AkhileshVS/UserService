package com.example.UserService.Service;

import com.example.UserService.Models.Token;
import com.example.UserService.Models.Users;
import com.example.UserService.Repository.TokenRepository;
import com.example.UserService.Repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public UserService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder,TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }
    public Users signUp(String fullName, String email, String password){
        Users u = new Users();
        u.setEmail(email);
        u.setName(fullName);
        u.setHashedPassword(bCryptPasswordEncoder.encode(password));
        Users user = userRepository.save(u);
        return user;
    }

    public Token login(String email, String paswword){
        Optional<Users> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            return null;
        }
        Users u  = user.get();
        if(!bCryptPasswordEncoder.matches(paswword,u.getHashedPassword())){
            return null;
        }
        LocalDate expiryDate= LocalDate.now().plus(30, ChronoUnit.DAYS);
        Date dDay = Date.from(expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Token token = new Token();
        token.setUser(u);
        token.setExpiryAt(dDay);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Token savedToken = tokenRepository.save(token);
        return savedToken;
    }

    public void logout(String token){
        Optional<Token> tokenoptional = tokenRepository.findByValueAndIsDeleted(token,false);
        if(tokenoptional.isEmpty()){
            //throw token not exixt
            return;
        }
        Token tk = tokenoptional.get();
        tk.setDeleted(true);
        tokenRepository.save(tk);
    }
    public Users validateToken(String token){
        Optional<Token> t = tokenRepository.findByValueAndIsDeletedAndExpiryAtGreaterThan(token,false,new Date());
        if(t.isEmpty()){
            return null;
        }
        
        return t.get().getUser();
    }
}
