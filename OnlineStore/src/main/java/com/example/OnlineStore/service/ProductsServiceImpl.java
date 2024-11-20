package com.example.OnlineStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.OnlineStore.model.Products;
import com.example.OnlineStore.repository.ProductsRepository;
import com.example.OnlineStore.DTO.ProductsDTO;
import com.example.OnlineStore.model.Category;
import com.example.OnlineStore.repository.CategoryRepository;
import com.example.OnlineStore.exception.ResourceNotFoundException; 

@Service
@Transactional
public class ProductsServiceImpl {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    public Products getById(Integer productId) {
        return productsRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", productId.toString())); 
    }

    public Products addProduct(ProductsDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("ProductDTO cannot be null");
        }

        Products product = new Products();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantityInInventory(productDTO.getQuantityInInventory());
        product.setDiscount(productDTO.getDiscount());
        product.setAvailableQuantity(productDTO.getAvailableQuantity());
        product.setWarehouseLocation(productDTO.getWarehouseLocation());

        if (productDTO.getCategory() != null) {
            Category category = categoryRepository.findById(productDTO.getCategory().getIdCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category", productDTO.getCategory().getIdCategory().toString())); 
            product.setCategory(category);
        }

        return productsRepository.save(product);
    }

    public Products update(Integer productId, ProductsDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("ProductDTO cannot be null");
        }

        Products existingProduct = productsRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", productId.toString()));

        
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantityInInventory(productDTO.getQuantityInInventory());
        existingProduct.setDiscount(productDTO.getDiscount());
        existingProduct.setAvailableQuantity(productDTO.getAvailableQuantity());
        existingProduct.setWarehouseLocation(productDTO.getWarehouseLocation());

        
        if (productDTO.getCategory() != null) {
            Category category = categoryRepository.findById(productDTO.getCategory().getIdCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category", productDTO.getCategory().getIdCategory().toString())); 
            existingProduct.setCategory(category);
        }

        return productsRepository.save(existingProduct);
    }

    public void delete(Integer productId) {
        if (!productsRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product", productId.toString()); 
        }
        productsRepository.deleteById(productId);
    }
}
