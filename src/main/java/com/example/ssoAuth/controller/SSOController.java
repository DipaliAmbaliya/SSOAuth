package com.example.ssoAuth.controller;

import com.example.ssoAuth.model.User;
import com.example.ssoAuth.service.JwtTokenService;
import com.example.ssoAuth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class SSOController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @RequestMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @RequestMapping("/")
    public String home1() {
        return "home";
    }
    @PostMapping("/login")
    public String login(String username, String password, HttpServletResponse response) {
        // Perform user authentication
        User authenticatedUser = userService.authenticate(username, password);

        if (authenticatedUser != null) {
            // Generate JWT token
            String token = jwtTokenService.generateToken(authenticatedUser);

            // set the token in a cookie or response header
            response.setHeader("Authorization", "Bearer " + token);

            return "redirect:/home";
        } else {
            // Authentication failed, redirect back to the login page
            return "redirect:/login?error";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/logout")
    public String logout() {
        //logout and token invalidation
        return "redirect:/login";
    }


// some methods here

//    @PostMapping("/registrate")
//    public String registrateUser( User user, Map<String, Object> map){
//        UserDetails userFromDatabase = userService.loadUserByUsername(user.getUsername());
//        if(userFromDatabase != null){
//            map.put("message", "User has been already registrated!");
//            return "registratePage";
//        }
//        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
//        user.setPassword(encodedPassword);
//        user.setRoles("USER");
//        userRepository.save(user);
//        map.put("message", "User has been successfully registrated!");
//        return redirectLoginPage;
//    }
}
