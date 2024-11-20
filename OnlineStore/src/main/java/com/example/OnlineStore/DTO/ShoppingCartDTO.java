package com.example.OnlineStore.DTO;

import java.io.Serializable;
import java.util.List;

public class ShoppingCartDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int idCart;
    private List<ProductsDTO> products; 
    private double total;
    private UserDTO user; 

    public ShoppingCartDTO() {}

    public ShoppingCartDTO(int idCart, List<ProductsDTO> products, double total, UserDTO user) {
        this.idCart = idCart;
        this.products = products;
        this.total = total;
        this.user = user; 
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public List<ProductsDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsDTO> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public UserDTO getUser() { 
        return user;
    }

    public void setUser(UserDTO user) { 
        this.user = user;
    }
}

