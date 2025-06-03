package com.interviewproject.cryptocurrency_platform.exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super("Insufficient Funds: " + message);
    }
}
