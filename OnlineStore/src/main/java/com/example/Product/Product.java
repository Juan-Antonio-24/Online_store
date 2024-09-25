package com.example.Product;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private int productId;
    private String name;    
    private String description;
    private double price;
    private int quantityInInventory;
    private String category;
    private double discount;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityInInventory() {
        return quantityInInventory;
    }

    public void setQuantityInInventory(int quantityInInventory) {
        this.quantityInInventory = quantityInInventory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void viewProductDetails() {
        // Método para ver los detalles del producto
    }

    public void updateStock(int quantity) {
        this.quantityInInventory += quantity;
    }

    public void addReview(String review) {
        // Agregar reseña
    }

    public double calculatePriceWithDiscount() {
        return price - (price * (discount / 100));
    }

    @Override
    public String toString() {
        return productId + "::" + name + "::" + description + "::" + price + "::" + quantityInInventory + "::" + category + "::" + discount;
    }
}
