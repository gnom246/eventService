package com.example.demo.dto;

import com.example.demo.entities.EventEntity;
import com.example.demo.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
