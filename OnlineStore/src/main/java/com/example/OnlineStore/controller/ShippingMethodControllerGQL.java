package com.example.OnlineStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;

import com.example.OnlineStore.DTO.ShippingMethodDTO;
import com.example.OnlineStore.model.ShippingMethod;
import com.example.OnlineStore.service.ShippingMethodService;

@Controller
public class ShippingMethodControllerGQL {

    private final ShippingMethodService service;


    public ShippingMethodControllerGQL(ShippingMethodService service) {
        this.service = service;
    }

    @QueryMapping
    public List<ShippingMethodDTO> getAllShippingMethods() {
        return service.getAll().stream()
                      .map(this::convertToDTO)
                      .collect(Collectors.toList());
    }

    @QueryMapping
    public ShippingMethodDTO getShippingMethodById(@Argument Integer shippingMethodId) {
        ShippingMethod shippingMethod = service.getById(shippingMethodId);
        return (shippingMethod != null) ? convertToDTO(shippingMethod) : null;
    }

    @MutationMapping
    public String createShippingMethod(@Argument ShippingMethodDTO shippingMethodDTO) {
        ShippingMethod shippingMethod = new ShippingMethod(shippingMethodDTO);
        service.createShippingMethod(shippingMethod);
        return "Shipping method created successfully";
    }

    @MutationMapping
    public String updateShippingMethod(@Argument Integer shippingMethodId, @Argument ShippingMethodDTO shippingMethodDTO) {
        ShippingMethod existingShippingMethod = service.getById(shippingMethodId);
        if (existingShippingMethod != null) {
            ShippingMethod shippingMethod = new ShippingMethod(shippingMethodDTO);
            shippingMethod.setIdShippingMethod(existingShippingMethod.getIdShippingMethod());
            service.createShippingMethod(shippingMethod);
            return "Shipping method updated successfully";
        }
        return "Shipping method not found";
    }

    @MutationMapping
    public String deleteShippingMethod(@Argument Integer shippingMethodId) {
        service.deleteShippingMethod(shippingMethodId);
        return "Shipping method deleted successfully";
    }

    private ShippingMethodDTO convertToDTO(ShippingMethod shippingMethod) {
        return new ShippingMethodDTO(
            shippingMethod.getIdShippingMethod(),
            shippingMethod.getShippingType(),
            shippingMethod.getShippingCost(),
            shippingMethod.getEstimatedTime(),
            shippingMethod.getShippingCompany()
        );
    }
}

