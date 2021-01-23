package com.example.demo.dto;

public class UsersDto {
    private String email;

    public UsersDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
