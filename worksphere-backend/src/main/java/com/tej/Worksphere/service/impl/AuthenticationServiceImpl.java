package com.tej.Worksphere.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.tej.Worksphere.dto.auth.AuthResponse;
import com.tej.Worksphere.dto.auth.LoginRequest;
import com.tej.Worksphere.security.jwt.JwtService;
import com.tej.Worksphere.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        String token = jwtService.generateToken(request.getUsername());

        return new AuthResponse(token);
    }
}