package com.interviewproject.cryptocurrency_platform.exceptions;

public class UserWithThisEmailNotFoundException extends RuntimeException {
    public UserWithThisEmailNotFoundException(String email) {
        super("User with this email not found: " + email);
    }
}
