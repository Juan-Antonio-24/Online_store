package com.example.OnlineStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.OnlineStore.model.ShippingMethod;
import com.example.OnlineStore.DTO.ShippingMethodDTO;
import com.example.OnlineStore.service.ShippingMethodService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "5. ShippingMethods", description = "Controller for managing shipping methods")
@RestController
@RequestMapping("/shipping_methods")  
@CrossOrigin(origins = "*")
public class ShippingMethodController {

    @Autowired
    private ShippingMethodService service;

    @Operation(summary = "Get all shipping methods", responses = {
        @ApiResponse(responseCode = "200", description = "Shipping methods found",
                     content = @Content(mediaType = "application/json",
                     array = @ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ShippingMethodDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No shipping methods found")
    })
    @GetMapping
    public List<ShippingMethodDTO> getAll() {
        return service.getAll().stream()
                      .map(this::convertToDTO)
                      .collect(Collectors.toList());
    }

    @Operation(summary = "Get shipping method by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Shipping method found",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ShippingMethodDTO.class))),
        @ApiResponse(responseCode = "404", description = "Shipping method not found")
    })
    @GetMapping("/{shippingMethodsId}")  
    public ResponseEntity<?> getById(@PathVariable Integer shippingMethodId) {
        ShippingMethod shippingMethod = service.getById(shippingMethodId);
        if (shippingMethod != null) {
            return new ResponseEntity<>(convertToDTO(shippingMethod), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Shipping method not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create a new shipping method", responses = {
        @ApiResponse(responseCode = "201", description = "Shipping method created successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ShippingMethodDTO shippingMethodDTO) {
        ShippingMethod shippingMethod = new ShippingMethod(shippingMethodDTO);
        service.createShippingMethod(shippingMethod);
        return new ResponseEntity<>("Shipping method created successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing shipping method", responses = {
        @ApiResponse(responseCode = "200", description = "Shipping method updated successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Shipping method not found")
    })
    @PutMapping("/{shippingMethodsId}")  
    public ResponseEntity<?> update(@RequestBody ShippingMethodDTO shippingMethodDTO, @PathVariable Integer shippingMethodId) {
        ShippingMethod auxShippingMethod = service.getById(shippingMethodId);
        if (auxShippingMethod != null) {
            ShippingMethod shippingMethod = new ShippingMethod(shippingMethodDTO);
            shippingMethod.setIdShippingMethod(auxShippingMethod.getIdShippingMethod());
            service.createShippingMethod(shippingMethod);
            return new ResponseEntity<>("Shipping method updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Shipping method not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a shipping method", responses = {
        @ApiResponse(responseCode = "200", description = "Shipping method deleted successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Shipping method not found")
    })
    @DeleteMapping("/{shippingMethodsId}")  
    public ResponseEntity<?> delete(@PathVariable Integer shippingMethodId) {
        service.deleteShippingMethod(shippingMethodId);
        return new ResponseEntity<>("Shipping method deleted successfully", HttpStatus.OK);
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
