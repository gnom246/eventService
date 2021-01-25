package com.example.demo.dao;

import com.example.demo.entities.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    void save(UserEntity userEntity) {
        entityManager.persist(userEntity);
    }

}

