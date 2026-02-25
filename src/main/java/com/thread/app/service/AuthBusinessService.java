package com.thread.app.service;

import com.thread.app.encoder.PasswordEncoder;
import com.thread.app.entity.User;
import com.thread.app.jwtUtil.JwtUtil;
import com.thread.app.repository.UserRepository;
import jakarta.inject.Singleton;

@Singleton
public class AuthBusinessService {

    private final UserRepository userRepository;
    private final JwtUtil jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthBusinessService(UserRepository userRepository,
                               JwtUtil jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(String username, String email, String password) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
       user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        return jwtService.generate(username);
    }

    public String login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generate(username);
    }
}