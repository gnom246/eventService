package com.example.demo.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String commentText;
    private LocalDateTime added;
    private String email;

    public CommentDto(Long id, String commentText, LocalDateTime added, String email) {
        this.id = id;
        this.commentText = commentText;
        this.added = added;
        this.email = email;
    }
}
