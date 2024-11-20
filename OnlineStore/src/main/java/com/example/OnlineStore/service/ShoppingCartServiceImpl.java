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
public class ShoppingCartServiceImpl {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public List<ShoppingCart> getAll() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart getById(Integer cartId) {
        return shoppingCartRepository.findById(cartId).orElse(null);
    }

    public ShoppingCart getCartByUserId(Integer userId) {
        return shoppingCartRepository.findByUser_UserId(userId);
    }

    public String add(ShoppingCart cart) {
        shoppingCartRepository.save(cart);
        return "Shopping cart added successfully";
    }

    public String update(ShoppingCart cart) {
        shoppingCartRepository.save(cart);
        return "Shopping cart updated successfully";
    }

    public String delete(Integer cartId) {
        shoppingCartRepository.deleteById(cartId);
        return "Shopping cart deleted successfully";
    }

    public String addProductToCart(Integer cartId, Products product) {
        ShoppingCart cart = getById(cartId);
        if (cart != null) {
            cart.addProduct(product);
            shoppingCartRepository.save(cart);
            return "Product added to cart successfully";
        } else {
            return "Shopping cart not found";
        }
    }

    public String removeProductFromCart(Integer cartId, Products product) {
        ShoppingCart cart = getById(cartId);
        if (cart != null) {
            cart.removeProduct(product);
            shoppingCartRepository.save(cart);
            return "Product removed from cart successfully";
        } else {
            return "Shopping cart not found";
        }
    }
}



