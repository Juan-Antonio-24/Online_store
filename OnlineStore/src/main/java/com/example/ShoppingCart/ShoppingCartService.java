package com.example.ShoppingCart;

import com.example.Product.Product;

public interface ShoppingCartService {

    boolean addProduct(int cartId, Product product);
    
    boolean removeProduct(int cartId, Product product);
    
    ShoppingCart getCart(int cartId);
    
    ShoppingCart[] getAllCarts();
}
