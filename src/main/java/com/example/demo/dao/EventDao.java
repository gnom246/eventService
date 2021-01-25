package com.example.demo.dao;

import com.example.demo.entities.EventEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class EventDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    void save(EventEntity eventEntity) {
        entityManager.persist(eventEntity);
    }
}

