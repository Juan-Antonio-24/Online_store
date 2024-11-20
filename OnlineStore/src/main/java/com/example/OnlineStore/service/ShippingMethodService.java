package com.example.OnlineStore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.OnlineStore.model.ShippingMethod;
import com.example.OnlineStore.DTO.ShippingMethodDTO;
import com.example.OnlineStore.repository.ShippingMethodRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShippingMethodService {

    @Autowired
    private ShippingMethodRepository repo;

    public List<ShippingMethod> getAll() {
        return repo.findAll();
    }

    public ShippingMethod getById(Integer shippingMethodId) {
        return repo.findById(shippingMethodId).orElse(null);
    }

    public ShippingMethod findById(Integer shippingMethodId) {
        return getById(shippingMethodId);
    }

    public void createShippingMethod(ShippingMethod shippingMethod) {
        repo.save(shippingMethod);
    }

    public void deleteShippingMethod(Integer shippingMethodId) {
        repo.deleteById(shippingMethodId);
    }

    
    public ShippingMethod convertToEntity(ShippingMethodDTO shippingMethodDTO) {
        ShippingMethod shippingMethod = new ShippingMethod();
        shippingMethod.setIdShippingMethod(shippingMethodDTO.getIdShippingMethod());
        shippingMethod.setShippingType(shippingMethodDTO.getShippingType());
        shippingMethod.setShippingCost(shippingMethodDTO.getShippingCost());
        shippingMethod.setEstimatedTime(shippingMethodDTO.getEstimatedTime());
        shippingMethod.setShippingCompany(shippingMethodDTO.getShippingCompany());
        return shippingMethod;
    }

    
    public List<ShippingMethodDTO> convertToDTO(List<ShippingMethod> shippingMethods) {
        return shippingMethods.stream()
                .map(shippingMethod -> new ShippingMethodDTO(
                        shippingMethod.getIdShippingMethod(),
                        shippingMethod.getShippingType(),
                        shippingMethod.getShippingCost(),
                        shippingMethod.getEstimatedTime(),
                        shippingMethod.getShippingCompany()))
                .collect(Collectors.toList());
    }
}

