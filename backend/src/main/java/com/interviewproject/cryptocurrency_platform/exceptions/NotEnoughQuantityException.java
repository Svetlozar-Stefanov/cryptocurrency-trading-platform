package com.interviewproject.cryptocurrency_platform.exceptions;

public class NotEnoughQuantityException extends RuntimeException {
    public NotEnoughQuantityException(String message) {
        super("Not enough quantity for transaction: " + message);
    }
}
