package com.example.backendokimmo.controllers;

import com.example.backendokimmo.exceptions.CustomException;
import com.example.backendokimmo.models.User;
import com.example.backendokimmo.requests.LoginRequest;
import com.example.backendokimmo.requests.RefreshRequest;
import com.example.backendokimmo.requests.RegisterRequest;
import com.example.backendokimmo.responses.AuthResponse;
import com.example.backendokimmo.services.JwtService;
import com.example.backendokimmo.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;


    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        User user = userService.register(request.getName(), request.getEmail(), request.getPassword());
        String token = jwtService.generateAccessToken(user.getEmail());
        String refresh = jwtService.generateRefreshToken(user.getEmail());
        return new AuthResponse(token, refresh);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        String token = jwtService.generateAccessToken(user.getEmail());
        String refresh = jwtService.generateRefreshToken(user.getEmail());
        return new AuthResponse(token, refresh);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        if (!jwtService.isTokenValid(refreshToken)) {
            throw new CustomException("Refresh token invalide ou expiré");
        }

        String email = jwtService.extractEmail(refreshToken);
        String newAccessToken = jwtService.generateAccessToken(email);
        String newRefreshToken = jwtService.generateRefreshToken(email);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
