package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
public class CommentFormDto {
    @Size(max = 500, message = "Description should have max 500 characters")
    private String body;
}
