/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import ProductDAO.ProductDAO;
import entity.Brand;
import entity.Category;
import entity.Product;
import java.sql.*;
import java.util.List;
/**
 *
 * @author admin
 */
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }
    
    public void addProduct(Product product) throws SQLException {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Giá sản phẩm không thể âm.");
        }
        productDAO.addProduct(product);
    }

    public Product getProductById(int id) {
        return productDAO.selectProduct(id);
    }

    public Product selectProduct(int id) {
        return productDAO.selectProduct(id);
    }
    
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public void updateProduct(Product product) throws SQLException {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Giá sản phẩm không thể âm.");
        }
        productDAO.updateProduct(product);
    }

    public void deleteProduct(int id) throws SQLException {
        productDAO.deleteProduct(id);
    }

    
     public List<Product> getProductsByCategoryId(int cateID) throws SQLException {
        return productDAO.getProductByCategoryId(cateID);
    }

    public List<Category> getAllCategories() throws SQLException {
        return productDAO.getAllCategories();
    }
    public List<Brand> getAllBrands() throws SQLException {
        return productDAO.getAllBrands();
    }

    public List<Product> searchProducts(String keyword) throws SQLException {
        return productDAO.searchProducts(keyword);
    }

    public List<Product> getProductsPaginated(int offset, int pageSize) throws SQLException {
        return productDAO.getProductsPaginated(offset, pageSize);
    }

    public int getProductsCount() throws SQLException {
        return productDAO.getProductsCount();
    }
}
