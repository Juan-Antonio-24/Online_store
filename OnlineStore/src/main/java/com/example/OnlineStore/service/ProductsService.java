package com.example.OnlineStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.OnlineStore.exception.ResourceNotFoundException;
import com.example.OnlineStore.model.Products;
import com.example.OnlineStore.model.Category;
import com.example.OnlineStore.repository.ProductsRepository;
import com.example.OnlineStore.repository.CategoryRepository;
import com.example.OnlineStore.DTO.ProductsDTO;

@Service
@Transactional
public class ProductsService {

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
        
        Products product = convertToEntity(productDTO);
        return productsRepository.save(product);
    }

    public Products update(Integer productId, ProductsDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("ProductDTO cannot be null");
        }
    
        
        productsRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", productId.toString()));
    
        
        Products updatedProduct = convertToEntity(productDTO);
        updatedProduct.setProductId(productId);
        return productsRepository.save(updatedProduct);
    }

    public void delete(Integer productId) {
        
        Products product = productsRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", productId.toString()));
        
        productsRepository.delete(product);
    }

    private Products convertToEntity(ProductsDTO productDTO) {
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

        return product;
    }
}



