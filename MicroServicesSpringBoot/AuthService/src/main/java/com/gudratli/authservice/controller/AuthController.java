package com.gudratli.authservice.controller;

import com.gudratli.authservice.entity.User;
import com.gudratli.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController
{
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login (@RequestBody User user)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated())
            return userService.generateToken(user.getUsername());
        else
            return null;
    }

    @PostMapping("/register")
    public User register (@RequestBody User user)
    {
        return userService.add(user);
    }

}
