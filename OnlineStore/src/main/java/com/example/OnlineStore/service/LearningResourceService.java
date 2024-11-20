package com.example.OnlineStore.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.example.OnlineStore.model.LearningResource;
import com.example.OnlineStore.repository.LearningResourceRepository;

@Service
@Transactional
public class LearningResourceService {
    @Autowired
    private LearningResourceRepository repo;

    public List<LearningResource> getAll() {
        return repo.findAll();
    }

    public void add(LearningResource resource) {
        repo.save(resource);
    }

    public void update(LearningResource resource, Integer id) {
        if (repo.existsById(id)) {
            resource.setId(id); 
            repo.save(resource);
        } else {
            throw new NoSuchElementException("No LearningResource found with id: " + id);
        }
    }

    public void delete(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new NoSuchElementException("No LearningResource found with id: " + id);
        }
    }
}

