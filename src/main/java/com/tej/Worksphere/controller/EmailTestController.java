package com.tej.Worksphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tej.Worksphere.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailTestController {

    private final EmailService emailService;

    @GetMapping("/test")
    public ResponseEntity<String> sendTestMail() {

        emailService.sendSimpleEmail(
        "tejgoti2005@gmail.com",
        "WorkSphere Test Email",
        "Congratulations! Your Spring Boot Email Service is working.");

        return ResponseEntity.ok("Email Sent Successfully");
    }
}