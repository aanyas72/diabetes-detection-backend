package com.imaginecup.diabetesapp.service;

import com.imaginecup.diabetesapp.controller.model.User;
import com.imaginecup.diabetesapp.repository.UserRepository;
import com.imaginecup.diabetesapp.repository.entity.JpaUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        JpaUserEntity jpaUserEntity = new JpaUserEntity();
        jpaUserEntity.setUsername(user.getUsername());
        jpaUserEntity.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userRepository.saveAndFlush(jpaUserEntity);
    }
}
