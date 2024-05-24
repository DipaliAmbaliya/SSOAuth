package com.example.ssoAuth.service;

import com.example.ssoAuth.model.User;

public interface JwtTokenService {
    String generateToken(User user);
    String getSecretKey();
}