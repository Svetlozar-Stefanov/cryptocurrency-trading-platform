package com.interviewproject.cryptocurrency_platform.controllers.user;

import com.interviewproject.cryptocurrency_platform.exceptions.UserWithThisEmailNotFoundException;
import com.interviewproject.cryptocurrency_platform.models.user.User;
import com.interviewproject.cryptocurrency_platform.repositories.user.UserRepository;
import com.interviewproject.cryptocurrency_platform.services.user.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/user")
    public User user(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        return userRepository.getUserByEmail(email);
    }

    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> reset(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        User user = userRepository.getUserByEmail(email);

        if (user == null) {
            throw new UserWithThisEmailNotFoundException(userDetails.getUsername());
        }

        userService.reset(user);
        return ResponseEntity.ok(Map.of("message", "User has been reset"));
    }
}

