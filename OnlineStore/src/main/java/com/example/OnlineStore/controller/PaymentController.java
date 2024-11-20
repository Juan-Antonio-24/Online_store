package com.example.OnlineStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.OnlineStore.model.Payment;
import com.example.OnlineStore.model.Orders;

import com.example.OnlineStore.service.PaymentService;
import com.example.OnlineStore.service.OrdersService;
import com.example.OnlineStore.DTO.PaymentDTO;
import com.example.OnlineStore.DTO.ProductsDTO;
import com.example.OnlineStore.DTO.ShippingMethodDTO;
import com.example.OnlineStore.DTO.ShoppingCartDTO;
import com.example.OnlineStore.DTO.UserDTO;
import com.example.OnlineStore.DTO.CategoryDTO; 

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "7. Payments", description = "Controller for managing payments")
@RestController
@RequestMapping("payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrdersService orderService;

    @Operation(summary = "Get all payments", responses = {
        @ApiResponse(responseCode = "200", description = "Payments found",
                     content = @Content(mediaType = "application/json",
                     array = @ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Payment.class)))),
        @ApiResponse(responseCode = "404", description = "No payments found")
    })
    @GetMapping
    public List<Payment> getAll() {
        return paymentService.getAll();
    }

    @Operation(summary = "Get payment by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Payment found",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Payment.class))),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("{paymentId}")
    public ResponseEntity<?> getById(@PathVariable Integer paymentId) {
        Payment payment = paymentService.getById(paymentId);
        if (payment != null) {
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Payment not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Process a new payment", responses = {
        @ApiResponse(responseCode = "201", description = "Payment processed successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @PostMapping("{orderId}")
    public ResponseEntity<?> processPayment(@PathVariable Integer orderId, @RequestBody PaymentDTO paymentDTO) {
        Orders order = orderService.getById(orderId);
        if (order == null) {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }

        
        List<ProductsDTO> productsDTOList = order.getCartProducts().stream()
            .map(product -> new ProductsDTO(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInInventory(),
                product.getDiscount(),
                product.getAvailableQuantity(),
                product.getWarehouseLocation(),
                new CategoryDTO() 
            ))
            .collect(Collectors.toList());

        ShoppingCartDTO cart = new ShoppingCartDTO(
            order.getShoppingCart().getIdCart(),
            productsDTOList,
            order.getCartTotal(),
            new UserDTO(order.getShoppingCart().getUser())
        );

        ShippingMethodDTO shippingMethod = new ShippingMethodDTO(
            order.getShippingMethod().getIdShippingMethod(),
            order.getShippingMethod().getShippingType(),
            order.getShippingCost(),
            order.getShippingMethod().getEstimatedTime(),
            order.getShippingMethod().getShippingCompany()
        );

        paymentService.processPayment(orderId, paymentDTO, cart, shippingMethod);

        return new ResponseEntity<>("Payment processed successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing payment", responses = {
        @ApiResponse(responseCode = "200", description = "Payment updated successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("{paymentId}")
    public ResponseEntity<?> update(@RequestBody PaymentDTO paymentDTO, @PathVariable Integer paymentId) {
        Payment auxPayment = paymentService.getById(paymentId);
        if (auxPayment != null) {
            paymentDTO.setIdPayment(auxPayment.getIdPayment());
            //paymentService.updatePayment(paymentDTO, new ShoppingCartDTO(), new ShippingMethodDTO());
            return new ResponseEntity<>("Payment updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Payment not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a payment", responses = {
        @ApiResponse(responseCode = "200", description = "Payment deleted successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @DeleteMapping("{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable Integer paymentId) {
        Payment payment = paymentService.getById(paymentId);
        if (payment != null) {
            paymentService.deletePayment(paymentId);
            return new ResponseEntity<>("Payment deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Payment not found", HttpStatus.NOT_FOUND);
        }
    }
}




