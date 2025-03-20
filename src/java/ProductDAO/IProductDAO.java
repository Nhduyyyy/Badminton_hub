package ProductDAO;

import entity.Brand;
import entity.Category;
import entity.Product;
import java.sql.SQLException;
import java.util.List;

public interface IProductDAO {
    void addProduct(Product product) throws SQLException;
    Product selectProduct(int id);
    List<Product> getAllProducts();
    void updateProduct(Product product) throws SQLException;
    void deleteProduct(int id) throws SQLException;
    List<Product> getProductByCategoryId(int id);
    int getCategoryIdByName(String categoryName) throws SQLException;
    void updateProductStock(int productId, int newStock) throws SQLException;
    List<Product> searchProductByName(String name) throws SQLException;
    List<Category> getAllCategories() throws SQLException;
    Brand getBrandByID(int ID);
    int getBrandIdByName(String brandName) throws SQLException;
    List<Brand> getAllBrands() throws SQLException;
    List<Product> searchProducts(String keyword) throws SQLException;
}


