package com.interviewproject.cryptocurrency_platform.exceptions;

public class UserWithEmailAlreadyExistsException extends RuntimeException {
    public UserWithEmailAlreadyExistsException(String email) {
        super("User with this email already exists: " + email);
    }
}
