package com.imaginecup.diabetesapp.repository;

import com.imaginecup.diabetesapp.repository.entity.JpaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<JpaUserEntity, Integer> {
    JpaUserEntity getJpaUserEntityByUsername(String username);
    boolean existsByUsername(String username);
}
