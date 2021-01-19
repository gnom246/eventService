package com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentText;

    private LocalDateTime added = LocalDateTime.now();

    @OneToOne
    @JoinTable(name = "who_add")
    private UserEntity userEntity;

    @ManyToOne
    @JoinTable(name = "to_event")
    private EventEntity eventEntity;
}
