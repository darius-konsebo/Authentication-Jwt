package org.example.authentication_jwt.controller;

import org.example.authentication_jwt.model.AuthRequest;
import org.example.authentication_jwt.model.AuthResponse;
import org.example.authentication_jwt.service.JwtService;
import org.example.authentication_jwt.service.MyUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    private JwtService jwtService;
    private MyUserDetailsService myUserDetailsService;
    private AuthenticationManager authenticationManager;

    public AuthController(JwtService jwtService, MyUserDetailsService myUserDetailsService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth/login")
    public AuthResponse Authlogin(@RequestBody AuthRequest req) {
        var auth = new UsernamePasswordAuthenticationToken(req.username(), req.password());
        authenticationManager.authenticate(auth);
        UserDetails user = myUserDetailsService.loadUserByUsername(req.username());
        String token = jwtService.generateToken(user.getUsername(), Map.of("roles", user.getAuthorities()));
        return new AuthResponse(token);
    }
    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Bonjour, endpoint protégé OK ");
    }
}
