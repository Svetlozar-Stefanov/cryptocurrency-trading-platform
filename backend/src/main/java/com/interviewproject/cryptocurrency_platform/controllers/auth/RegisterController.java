package com.interviewproject.cryptocurrency_platform.controllers.auth;

import com.interviewproject.cryptocurrency_platform.models.auth.RegisterRequest;
import com.interviewproject.cryptocurrency_platform.services.auth.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest) {
        registerService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
