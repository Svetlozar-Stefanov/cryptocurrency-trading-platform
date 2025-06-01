package com.interviewproject.cryptocurrency_platform.services.auth;

import com.interviewproject.cryptocurrency_platform.exceptions.UserWithEmailAlreadyExistsException;
import com.interviewproject.cryptocurrency_platform.models.auth.RegisterRequest;
import com.interviewproject.cryptocurrency_platform.models.user.User;
import com.interviewproject.cryptocurrency_platform.repositories.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest registerRequest) {
        if (userRepository.userWithEmailExists(registerRequest.email())){
            throw new UserWithEmailAlreadyExistsException(registerRequest.email());
        }

        User user = new User();
        user.setName(registerRequest.name());
        user.setEmail(registerRequest.email());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setRole("USER");

        userRepository.addUser(user);
    }
}
