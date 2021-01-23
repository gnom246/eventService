package com.example.demo.services;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserDao;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.NewUserFormDto;
import com.example.demo.entities.RoleEntity;
import com.example.demo.entities.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Optional<UserEntity> findUser(NewUserFormDto newUserFormDto) {
        return userRepository.findUserByEmail(newUserFormDto.getEmail());
    }
    @Transactional
    public void registerUser(NewUserFormDto newUserFormDto) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail(newUserFormDto.getEmail());
        userEntity.setNickname(newUserFormDto.getNickname());

        userEntity.setPassword(passwordEncoder.encode(newUserFormDto.getPassword()));

        final String roleName = "USER";
        RoleEntity roleEntity = roleRepository
                .findRolesEntityByRoleName(roleName)
                .orElseGet(() -> roleRepository.save(new RoleEntity(roleName)));

        userEntity.addRole(roleEntity);

        userRepository.save(userEntity);
    }
}
