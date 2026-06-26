package com.tej.Worksphere.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tej.Worksphere.dto.auth.AuthResponse;
import com.tej.Worksphere.dto.auth.LoginRequest;
import com.tej.Worksphere.security.jwt.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> generateToken(
            @Valid @RequestBody LoginRequest request) {

        String token = jwtService.generateToken(request.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}