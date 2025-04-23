package com.example.demo_rest.exception;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}