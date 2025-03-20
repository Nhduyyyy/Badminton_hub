/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author PC Windows 10
 */


import java.time.LocalDateTime;


public class Product {
    private int productId;
    private String productName;
    private String description;
    private double price;
    private int stock;
    private String status;
    private int brandId;
    private int categoryId;
    private String image;
    private LocalDateTime createdAt;

    
    public Product() {
    }

    public Product(int productId, String productName, String description, double price, int stock, String status, int brandId, int categoryId, String image, LocalDateTime createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.image = image;
        this.createdAt = createdAt;
    }

    public Product(String productName, String description, double price, int stock, String status, int brandId, int categoryId, String image) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.image = image;
    }

    
    
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status='" + status + '\'' +
                ", brandId=" + brandId +
                ", categoryId=" + categoryId +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

