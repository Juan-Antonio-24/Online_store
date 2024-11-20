package com.example.OnlineStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.OnlineStore.model.Products;
import com.example.OnlineStore.model.Category;
import com.example.OnlineStore.service.ProductsService;
import com.example.OnlineStore.service.CategoryService;
import com.example.OnlineStore.DTO.ProductsDTO;
import com.example.OnlineStore.DTO.CategoryDTO;

@Controller
public class ProductsControllerGQL {

    private final ProductsService productsService;
    private final CategoryService categoryService;

    public ProductsControllerGQL(ProductsService productsService, CategoryService categoryService) {
        this.productsService = productsService;
        this.categoryService = categoryService;
    }

    private ProductsDTO convertToProductDTO(Products product) {
        Category category = product.getCategory();
        CategoryDTO categoryDTO = (category != null) ? new CategoryDTO(
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

    @QueryMapping
    public List<ProductsDTO> getAllProducts() {
        return productsService.getAllProducts().stream()
                              .map(this::convertToProductDTO)
                              .collect(Collectors.toList());
    }

    @QueryMapping
    public ProductsDTO getProductById(@Argument Integer id) {
        Products product = productsService.getById(id);
        return (product != null) ? convertToProductDTO(product) : null;
    }

    @MutationMapping
    public ProductsDTO addProduct(@Argument ProductsDTO productDTO) {
        if (productDTO == null || 
            (productDTO.getCategory() != null && categoryService.getCategoryById(productDTO.getCategory().getIdCategory()) == null)) {
            throw new IllegalArgumentException("Invalid category ID");
        }

        Products createdProduct = productsService.addProduct(productDTO);
        return convertToProductDTO(createdProduct);
    }

    @MutationMapping
    public ProductsDTO updateProduct(@Argument Integer id, @Argument ProductsDTO productDTO) {
        Products product = productsService.getById(id);
        if (product == null) {
            return null;
        }

        populateProductFromDTO(product, productDTO);
        Products updatedProduct = productsService.update(id, productDTO);
        return convertToProductDTO(updatedProduct);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Integer id) {
        if (productsService.getById(id) == null) {
            return false;
        }
        productsService.delete(id);
        return true;
    }
}


