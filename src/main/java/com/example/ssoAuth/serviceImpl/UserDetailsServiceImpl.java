package com.example.ssoAuth.serviceImpl;

import com.example.ssoAuth.model.User;
import com.example.ssoAuth.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    private static final Map<String, String> users = new HashMap<>();

    static {
        //Password should be hashed but for simplicity we are displaying it like this
        users.put("john", "password123");
        users.put("jane", "password456");
    }

    @Override
    public User authenticate(String username, String password) {
        String storedPassword = users.get(username);
        if (storedPassword != null && storedPassword.equals(password)) {
            // User authenticated successfully
            return new User(username, null);
        }
        return null; // Authentication failed
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String storedPassword = users.get(username);
        if (storedPassword != null) {
            //return new org.springframework.security.core.userdetails.User(username, new BCryptPasswordEncoder().encode(storedPassword),null);

            return  org.springframework.security.core.userdetails.User.builder()
                    .username(username)
                    .password(new BCryptPasswordEncoder().encode(storedPassword))
                    .roles("USER") // Provide user roles if needed
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
