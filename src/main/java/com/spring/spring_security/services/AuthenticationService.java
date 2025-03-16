package com.spring.spring_security.services;

import com.spring.spring_security.entities.User;
import com.spring.spring_security.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final JwtService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public AuthenticationService(JwtService jwtService, JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository){
        this.jwtService = jwtService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public String authenticate(User user){
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        boolean isValid = bCryptPasswordEncoder.matches(user.getPassword(), userOptional.get().getPassword());
        if(!isValid){
            throw new BadCredentialsException("errado!");
        }
        return jwtService.generateToken(user);
    }
}
