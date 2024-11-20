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
public class ShippingMethodServiceImpl {

    @Autowired
    private ShippingMethodRepository shippingMethodRepository;

    public List<ShippingMethodDTO> getAll() {
        List<ShippingMethod> shippingMethods = shippingMethodRepository.findAll();
        return convertToDTO(shippingMethods);
    }

    public ShippingMethodDTO getById(Integer shippingMethodId) {
        ShippingMethod shippingMethod = shippingMethodRepository.findById(shippingMethodId).orElse(null);
        return shippingMethod != null ? convertToDTO(shippingMethod) : null;
    }

    public void createShippingMethod(ShippingMethodDTO shippingMethodDTO) {
        ShippingMethod shippingMethod = convertToEntity(shippingMethodDTO);
        shippingMethodRepository.save(shippingMethod);
    }

    public void deleteShippingMethod(Integer shippingMethodId) {
        shippingMethodRepository.deleteById(shippingMethodId);
    }

    
    private ShippingMethod convertToEntity(ShippingMethodDTO shippingMethodDTO) {
        ShippingMethod shippingMethod = new ShippingMethod();
        shippingMethod.setIdShippingMethod(shippingMethodDTO.getIdShippingMethod());
        shippingMethod.setShippingType(shippingMethodDTO.getShippingType());
        shippingMethod.setShippingCost(shippingMethodDTO.getShippingCost());
        shippingMethod.setEstimatedTime(shippingMethodDTO.getEstimatedTime());
        shippingMethod.setShippingCompany(shippingMethodDTO.getShippingCompany());
        return shippingMethod;
    }

   
    private ShippingMethodDTO convertToDTO(ShippingMethod shippingMethod) {
        return new ShippingMethodDTO(
                shippingMethod.getIdShippingMethod(),
                shippingMethod.getShippingType(),
                shippingMethod.getShippingCost(),
                shippingMethod.getEstimatedTime(),
                shippingMethod.getShippingCompany());
    }

    
    private List<ShippingMethodDTO> convertToDTO(List<ShippingMethod> shippingMethods) {
        return shippingMethods.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

