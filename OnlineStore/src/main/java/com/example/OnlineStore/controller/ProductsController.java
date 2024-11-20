package com.example.OnlineStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.OnlineStore.model.Products;
import com.example.OnlineStore.model.Category;
import com.example.OnlineStore.service.ProductsService;
import com.example.OnlineStore.service.CategoryService;
import com.example.OnlineStore.DTO.ProductsDTO;  
import com.example.OnlineStore.DTO.CategoryDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "2. Products", description = "Controller for managing products")
@RestController
@RequestMapping("products")
@CrossOrigin(origins = "*")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private CategoryService categoryService;

    private ProductsDTO convertToProductDTO(Products product) {
        Category category = product.getCategory();
        CategoryDTO categoryDTO = category != null ? new CategoryDTO(
            category.getIdCategory(),
            category.getName(),
            category.getDescription()
        ) : null;
        
        return new ProductsDTO(
            product.getProductId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantityInInventory(),
            product.getDiscount(),
            product.getAvailableQuantity(),
            product.getWarehouseLocation(),
            categoryDTO 
        );
    }

    private void populateProductFromDTO(Products product, ProductsDTO productDTO) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantityInInventory(productDTO.getQuantityInInventory());
        product.setDiscount(productDTO.getDiscount());
        product.setAvailableQuantity(productDTO.getAvailableQuantity());
        product.setWarehouseLocation(productDTO.getWarehouseLocation());

        if (productDTO.getCategory() != null) {
            Category category = categoryService.getCategoryById(productDTO.getCategory().getIdCategory());
            if (category != null) {
                product.setCategory(category);
            } else {
                throw new IllegalArgumentException("Invalid category ID");
            }
        }
    }

    @Operation(summary = "Get all products", responses = {
        @ApiResponse(responseCode = "200", description = "Products found",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = ProductsDTO.class))),  
        @ApiResponse(responseCode = "404", description = "Products not found")
    })
    @GetMapping
    public ResponseEntity<List<ProductsDTO>> getAllProducts() {
        List<Products> products = productsService.getAllProducts();
        List<ProductsDTO> productDTOs = products.stream()
            .map(this::convertToProductDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    @Operation(summary = "Get product by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Product found",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = ProductsDTO.class))),  
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductsDTO> getProductById(@PathVariable Integer id) {
        Products product = productsService.getById(id);
        return product != null ? ResponseEntity.ok(convertToProductDTO(product)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Add a new product", responses = {
        @ApiResponse(responseCode = "201", description = "Product created",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = ProductsDTO.class))),  
        @ApiResponse(responseCode = "400", description = "Invalid category ID")
    })
    @PostMapping
    public ResponseEntity<ProductsDTO> addProduct(@RequestBody ProductsDTO productDTO) {
        if (productDTO == null || 
            (productDTO.getCategory() != null && categoryService.getCategoryById(productDTO.getCategory().getIdCategory()) == null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Products createdProduct = productsService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToProductDTO(createdProduct));
    }

    @Operation(summary = "Update a product by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Product updated",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = ProductsDTO.class))),  
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductsDTO> updateProduct(@PathVariable Integer id, @RequestBody ProductsDTO productDTO) {
        Products product = productsService.getById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        populateProductFromDTO(product, productDTO);
        Products updatedProduct = productsService.update(id, productDTO);
        return ResponseEntity.ok(convertToProductDTO(updatedProduct));
    }

    @Operation(summary = "Delete a product by ID", responses = {
        @ApiResponse(responseCode = "204", description = "Product deleted"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        if (productsService.getById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        productsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


