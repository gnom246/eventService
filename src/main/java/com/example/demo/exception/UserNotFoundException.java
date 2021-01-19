package com.example.demo.exception;

public class UserNotFoundException extends RuntimeException{
    private String email;

    public UserNotFoundException(String email) {
        this.email = email;
    }
    @Override
    public String getMessage() {
        return String.format("User not found: %s", email);
    }

}
