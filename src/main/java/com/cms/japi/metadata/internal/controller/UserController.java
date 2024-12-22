package com.cms.japi.metadata.internal.controller;

import com.cms.japi.metadata.internal.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.lang.String;

import com.cms.japi.metadata.internal.entity.User;

@RestController
@RequestMapping(path="/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path="/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) //veoma sus
    {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);

        //password treba hashovati !!!
        newUser.setPassword(password);

        try
        {
            userRepository.save(newUser);
        }
        catch (Exception e)
        {
            return "Error while registering user: " + e.getMessage();
        }

        return "User " + username + " registered successfully";
    }

    @PostMapping(path="/login")
    public String loginUser(
            @RequestParam String username,
            @RequestParam String password)
    {
        //zavrsiti
        return "";
    }

}
