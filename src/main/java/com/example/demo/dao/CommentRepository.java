package com.example.demo.dao;

import com.example.demo.entities.CommentEntity;
import com.example.demo.entities.EventEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByEventEntity_Id(Long eventId, Sort sort);
}
