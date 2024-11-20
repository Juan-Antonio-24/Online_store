package com.example.OnlineStore.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("The provided credentials are invalid. Please check your username and password.");
    }
}
