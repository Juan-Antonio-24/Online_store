package com.example.OnlineStore.DTO;

public class ProductsDTO {

    private Integer productId;
    private String name;
    private String description;
    private double price;
    private int quantityInInventory;
    private double discount;
    private int availableQuantity;
    private String warehouseLocation;
    private CategoryDTO category;  

    public ProductsDTO() {}

    public ProductsDTO(Integer productId, String name, String description, double price, int quantityInInventory,
                      double discount, int availableQuantity, String warehouseLocation, CategoryDTO category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityInInventory = quantityInInventory;
        this.discount = discount;
        this.availableQuantity = availableQuantity;
        this.warehouseLocation = warehouseLocation;
        this.category = category;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
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
        if (price < 0) throw new IllegalArgumentException("The price cannot be negative.");
        this.price = price;
    }

    public int getQuantityInInventory() {
        return quantityInInventory;
    }

    public void setQuantityInInventory(int quantityInInventory) {
        if (quantityInInventory < 0) throw new IllegalArgumentException("The quantity in inventory cannot be negative.");
        this.quantityInInventory = quantityInInventory;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        if (discount < 0) throw new IllegalArgumentException("The discount cannot be negative.");
        this.discount = discount;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        if (availableQuantity < 0) throw new IllegalArgumentException("The available quantity cannot be negative.");
        this.availableQuantity = availableQuantity;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }


}
