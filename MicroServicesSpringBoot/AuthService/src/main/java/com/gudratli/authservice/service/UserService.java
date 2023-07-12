package com.gudratli.authservice.service;

import com.gudratli.authservice.entity.User;
import com.gudratli.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserService implements UserDetailsService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User getByUsername (String username)
    {
        return userRepository.findByUsername(username);
    }

    public User add (User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String generateToken (final String username)
    {
        return jwtService.generateAccessToken(userRepository.findByUsername(username));
    }

    public void validateToken (String token, UserDetails userDetails)
    {
        jwtService.isAccessTokenValid(token, userDetails);
    }

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException
    {
        return getByUsername(username);
    }
}
