package com.example.OnlineStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.OnlineStore.model.User;
import com.example.OnlineStore.repository.UserRepository;
import com.example.OnlineStore.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll(); 
    }

    public User getByControlNumber(Integer controlNumber) {
        return userRepository.findById(controlNumber)
            .orElseThrow(() -> new ResourceNotFoundException("User", controlNumber.toString())); 
    }

    public String register(User user) {
        userRepository.save(user); 
        return "User registered successfully"; 
    }

    public String update(User user) {
        if (!userRepository.existsById(user.getUserId())) {
            throw new ResourceNotFoundException("User", user.getUserId().toString()); 
        }
        userRepository.save(user); 
        return "User updated successfully"; 
    }

    public String delete(Integer controlNumber) {
        if (!userRepository.existsById(controlNumber)) {
            throw new ResourceNotFoundException("User", controlNumber.toString()); 
        }
        userRepository.deleteById(controlNumber); 
        return "User deleted successfully"; 
    }
}






