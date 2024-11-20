package com.example.OnlineStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.OnlineStore.model.Products;
import com.example.OnlineStore.model.ShoppingCart;
import com.example.OnlineStore.repository.ShoppingCartRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShoppingCartService {
    
    @Autowired
    private ShoppingCartRepository repo;

    public List<ShoppingCart> getAll() {
        return repo.findAll();
    }

    public ShoppingCart save(ShoppingCart shoppingCart) {
        return repo.save(shoppingCart); 
    }

    public ShoppingCart getById(Integer cartId) {
        return repo.findById(cartId).orElse(null);
    }

    public void delete(Integer cartId) {
        repo.deleteById(cartId);
    }

    public void removeProductFromCart(Integer cartId, Products product) {
        ShoppingCart shoppingCart = getById(cartId);
        if (shoppingCart != null) {
            shoppingCart.removeProduct(product);
            save(shoppingCart); 
        } else {
            System.out.println("Shopping cart not found!");
        }
    }

    public ShoppingCart getCartByUserId(Integer userId) {
        return repo.findByUser_UserId(userId);
    }
    
    public void addProductToCart(Integer cartId, Products product) {
        ShoppingCart shoppingCart = getById(cartId);
        if (shoppingCart != null) {
            shoppingCart.addProduct(product);
            save(shoppingCart);
        } else {
            System.out.println("Shopping cart not found!");
        }
    }
}








