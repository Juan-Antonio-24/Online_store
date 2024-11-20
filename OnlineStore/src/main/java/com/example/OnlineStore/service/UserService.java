package com.example.OnlineStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.OnlineStore.model.User;
import com.example.OnlineStore.repository.UserRepository;
import com.example.OnlineStore.exception.ResourceNotFoundException;
import com.example.OnlineStore.exception.InvalidCredentialsException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository repo;

    public List<User> getAll() {
        return repo.findAll();
    }

    public void save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        repo.save(user);
    }

    public User getById(Integer userId) {
        return repo.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
    }

    public void delete(Integer userId) {
        if (!repo.existsById(userId)) {
            throw new ResourceNotFoundException("User", userId.toString());
        }
        repo.deleteById(userId);
    }

    public User authenticate(String email, String password) {
        User user = repo.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new InvalidCredentialsException(); 
        }
        return user;
    }
}





