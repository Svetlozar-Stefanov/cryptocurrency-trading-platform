package com.interviewproject.cryptocurrency_platform.controllers.user;

import com.interviewproject.cryptocurrency_platform.models.user.User;
import com.interviewproject.cryptocurrency_platform.repositories.user.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public User user(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        return userRepository.getUserByEmail(email);
    }
}

