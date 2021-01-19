package com.example.demo.dao;

import com.example.demo.entities.EventEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository <EventEntity, Long> {
    List<EventEntity> findAllByEndDate(LocalDate localDate, Sort sort);
    List<EventEntity> findAll();
    Optional<EventEntity> findById(Long id);
    Optional<EventEntity> findEventEntityById(Long id, Sort sort);
    List<EventEntity> findByTitleContaining(String titlePart, Sort sort);
    List<EventEntity> findByTitleContainingAndStartDateAfter(String titlePart, LocalDate localDate, Sort sort);
    List<EventEntity> findByTitleContainingAndEndDateAfter(String titlePart, LocalDate localDate, Sort sort);

}
