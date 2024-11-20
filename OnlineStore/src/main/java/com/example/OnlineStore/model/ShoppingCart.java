package com.example.OnlineStore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonManagedReference(value = "cartOrder")
    private Orders order;


    @ManyToMany
    @JoinTable(
        name = "Cart_Products", 
        joinColumns = @JoinColumn(name = "idCart"), 
        inverseJoinColumns = @JoinColumn(name = "idProduct") 
    )
    @JsonManagedReference
    private List<Products> products; 

    private double total;

    @ManyToOne 
    private User user;  

    public ShoppingCart() {
        this.products = new ArrayList<>(); 
        this.total = 0.0; 
    }

    public void addProduct(Products product) { 
        if (!products.contains(product)) { 
            products.add(product);
            calculateTotal();
        } else {
            System.out.println("The product is already in the cart");
        }
    }
    
    public void removeProduct(Products product) { 
        if (products.contains(product)) { 
            products.remove(product);
            calculateTotal(); 
        } else {
            System.out.println("The product is not in the cart");
        }
    }
    
    public double calculateTotal() {
        total = 0.0; 
        for (Products product : products) { 
            total += product.getPrice(); 
        }
        return total;
    }

    public void emptyCart() {
        products.clear(); 
        total = 0.0; 
    }

    public void applyDiscountCoupon(String coupon) {
        
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public List<Products> getProducts() { 
        return products;
    }

    public List<Integer> getProductsId() {
        List<Integer> productIds = new ArrayList<>();
        for (Products product : products) {
            productIds.add(product.getProductId()); 
        }
        return productIds;
    }
    
    public void setProducts(List<Products> products) {
        this.products = products;
        calculateTotal(); 
    }

    public double getTotal() {
        return total; 
    }

    public User getUser() {  
        return user;
    }

    public void setUser(User user) {  
        this.user = user;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ShoppingCart cart = (ShoppingCart) obj;
        return idCart == cart.idCart; 
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCart); 
    }
}
