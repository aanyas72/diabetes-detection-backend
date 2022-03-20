package com.imaginecup.diabetesapp.controller;

import com.imaginecup.diabetesapp.controller.model.User;
import com.imaginecup.diabetesapp.repository.entity.JpaUserEntity;
import com.imaginecup.diabetesapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public JpaUserEntity login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping
    public String test() {
        return "Authorization granted!";
    }

    @PostMapping(path = "/add")
    public void createUser(@RequestBody User user) {
        userService.addUser(user);
    }
}
