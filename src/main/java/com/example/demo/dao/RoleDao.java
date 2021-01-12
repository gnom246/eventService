package com.example.demo.dao;

import com.example.demo.entities.RoleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    void save(RoleEntity roleEntity) {
        entityManager.persist(roleEntity);
    }
}
