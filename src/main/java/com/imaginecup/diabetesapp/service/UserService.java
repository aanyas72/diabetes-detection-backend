package com.imaginecup.diabetesapp.service;

import com.imaginecup.diabetesapp.controller.model.User;
import com.imaginecup.diabetesapp.repository.UserRepository;
import com.imaginecup.diabetesapp.repository.entity.JpaUserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        JpaUserEntity jpaUserEntity = new JpaUserEntity();
        jpaUserEntity.setUsername(user.getUsername());
        jpaUserEntity.setPassword(encoder().encode(user.getPassword()));

        userRepository.saveAndFlush(jpaUserEntity);
    }

    public JpaUserEntity login(User user) {
        String username = user.getUsername();

        if (userRepository.existsByUsername(username) && (BCrypt.checkpw(user.getPassword(),
                userRepository.getJpaUserEntityByUsername(username).getPassword()))) {
            String token = getJWTToken(username);
            JpaUserEntity currentUser = userRepository.getJpaUserEntityByUsername(username);
            currentUser.setToken(token);
            userRepository.saveAndFlush(currentUser);
            return currentUser;
        } else {
            return null;
        }
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
