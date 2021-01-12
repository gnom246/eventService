package com.example.demo.dao;

import com.example.demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByEmail(String email);
}
