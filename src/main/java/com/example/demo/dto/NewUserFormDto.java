package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.StringJoiner;

@Setter
@Getter
public class NewUserFormDto {
    public NewUserFormDto() {
    }
    @Email(message = "User already exists")
    private String email;
    @Size(max = 50, message = "Nickname should have max 50 letters and should not be empty and should not have only white spaces")
    @NotBlank(message = "Nickname should have max 50 letters and should not be empty and should not have only white spaces")
    private String nickname;
    @Size(min = 8, max = 30, message = "Password should have 8-30 letters")
    private String password;

    @Override
    public String toString() {
        return new StringJoiner(", ", NewUserFormDto.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .add("nickname='" + nickname + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
