package com.spring.spring_security.services;

import com.spring.spring_security.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    private final JwtEncoder encoder;

    public JwtService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(User user){
        Instant now = Instant.now();
        long expiry = 3600L;

        var claims = JwtClaimsSet.builder()
                .issuer("spring-security")
                .subject(user.getUsername())
                .claim("username", user.getUsername())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
