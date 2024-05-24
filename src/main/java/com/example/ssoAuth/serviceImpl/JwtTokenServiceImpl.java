package com.example.ssoAuth.serviceImpl;

import com.example.ssoAuth.model.User;
import com.example.ssoAuth.service.JwtTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.security.jwt.expiration}")
    private long expiration;

    @Override
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, getSecretKey())
                .compact();
    }

    @Override
    public String getSecretKey() {
        // You might want to provide a more secure way to retrieve or generate the secret key
        // For simplicity, we are using the configured secret key from properties in this example
        return secretKey;
    }

}
