package com.example.ssoAuth.service;

import com.example.ssoAuth.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    User authenticate(String username, String password);
    UserDetails loadUserByUsername(String username);
}
