package com.example.OnlineStore.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.OnlineStore.model.User;
import com.example.OnlineStore.service.UserService;

@Controller
public class UserControllerGQL {

    private final UserService service;

    public UserControllerGQL(UserService service) {
        this.service = service;
    }

    @QueryMapping
    public List<User> getAllUsers() {
        return service.getAll();
    }

    @QueryMapping
    public User getUserById(@Argument Integer userId) {
        return service.getById(userId);
    }

    @MutationMapping
    public String registerUser(@Argument User user) {
        service.save(user);
        return "User registered successfully";
    }

    @MutationMapping
    public String updateUser(@Argument User user, @Argument Integer userId) {
        User existingUser = service.getById(userId);
        if (existingUser != null) {
            user.setUserId(existingUser.getUserId());
            service.save(user);
            return "User updated successfully";
        } else {
            return "User not found";
        }
    }

    @MutationMapping
    public String deleteUser(@Argument Integer userId) {
        service.delete(userId);
        return "User deleted successfully";
    }
}
