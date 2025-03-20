package ProductDAO;

import context.DBContext;
import entity.Brand;
import entity.Category;
import entity.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO Products (product_name,description,price,stock,status,brand_id,category_id,image,created_at)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getStock());
            pstmt.setString(5, product.getStatus());
            pstmt.setInt(6, product.getBrandId());
            pstmt.setInt(7, product.getCategoryId());
            pstmt.setString(8, product.getImage());
            pstmt.setTimestamp(9, (product.getCreatedAt() != null) ? Timestamp.valueOf(product.getCreatedAt()) : null);
            pstmt.executeUpdate();
        }
    }

    public Product selectProduct(int id) {
        String sql = "SELECT * FROM Products WHERE product_id = ?";

        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("created_at");
                LocalDateTime importDate = (timestamp != null) ? timestamp.toLocalDateTime() : null;

                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("status"),
                        rs.getInt("brand_id"),
                        rs.getInt("category_id"),
                        rs.getString("image"),
                        importDate
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";

        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("created_at");
                LocalDateTime importDate = (timestamp != null) ? timestamp.toLocalDateTime() : null;

                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("status"),
                        rs.getInt("brand_id"),
                        rs.getInt("category_id"),
                        rs.getString("image"),
                        importDate
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE Products SET product_name = ?, description = ?, price = ?, stock = ?, status = ?, brand_id = ?, category_id = ?, image = ? WHERE product_id = ?";

        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getStock());
            pstmt.setString(5, product.getStatus());
            pstmt.setInt(6, product.getBrandId());
            pstmt.setInt(7, product.getCategoryId());
            pstmt.setString(8, product.getImage());
            pstmt.setInt(9, product.getProductId());
            pstmt.executeUpdate();
        }
    }

    public void deleteProduct(int id) throws SQLException {
        String sql = "UPDATE Products SET stock = 0 WHERE product_id = ?";

        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Product> getProductByCategoryId(int id) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE category_id = ?";

        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("created_at");
                LocalDateTime importDate = (timestamp != null) ? timestamp.toLocalDateTime() : null;

                productList.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("status"), rs.getInt("brand_id"),
                        rs.getInt("category_id"),
                        rs.getString("image"),
                        importDate
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public int getCategoryIdByName(String categoryName) throws SQLException {
        String query = "SELECT category_id FROM Categories WHERE category_name = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, categoryName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("category_id");
                }
            }
        }
        return -1;
    }

    public int getStockByProductId(int productId) throws SQLException {
        String sql = "SELECT stock FROM Products WHERE product_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("stock");
            }
        }
        return -1;
    }

    public void updateProductStock(int productId, int newStock) throws SQLException {
        String sql = "UPDATE Products SET stock = ?, status = ? WHERE product_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String newStatus = (newStock <= 0) ? "Hết hàng" : "Còn hàng";

            stmt.setInt(1, newStock);
            stmt.setString(2, newStatus);
            stmt.setInt(3, productId);

            stmt.executeUpdate();
        }
    }

    public List<Product> searchProductByName(String name) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE product_name LIKE ?";

        try (Connection con = DBContext.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp("created_at");
                LocalDateTime importDate = (timestamp != null) ? timestamp.toLocalDateTime() : null;

                products.add(new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock"),
                        resultSet.getString("status"),
                        resultSet.getInt("brand_id"),
                        resultSet.getInt("category_id"),
                        resultSet.getString("image"),
                        importDate));
            }
        }
        return products;
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT category_id, category_name, created_at FROM Categories";

        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setcId(rs.getInt("category_id"));
                category.setcName(rs.getString("category_name"));
                category.setCreatedAt(rs.getTimestamp("created_at"));
                categories.add(category);
            }
        }
        return categories;
    }

    public Brand getBrandByID(int ID) {
        String sql = "SELECT * FROM Brands WHERE brand_id =  ?";
        try (
                Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            Product product = selectProduct(ID);
            if (product == null) {
                return null;
            }
            pstmt.setInt(1, product.getBrandId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Brand(rs.getInt("brand_id"),
                            rs.getString("brand_name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getBrandIdByName(String brandName) throws SQLException {
        int brandId = -1;
        String sql = "SELECT brand_id FROM Brands WHERE brand_name = ?";

        try (
                Connection conn = DBContext.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, brandName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                brandId = rs.getInt("brand_id");
            }
        }
        return brandId;
    }

    public List<Brand> getAllBrands() throws SQLException {
        List<Brand> brandList = new ArrayList<>();
        String sql = "SELECT * FROM Brands";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                brandList.add(new Brand(rs.getInt("brand_id"), rs.getString("brand_name")));
            }
        }
        return brandList;
    }

    public int getCurrentStock(int productId) throws SQLException {
        String query = "SELECT stock FROM Products WHERE product_id = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("stock");
            }
        }
        return 0;
    }

    public List<Product> getFilteredProducts(String categoryId, String[] priceRanges, String[] brands) {
        List<Product> productList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection(); // Đảm bảo lấy được kết nối
            if (conn == null) {
                System.out.println("Lỗi: Không thể kết nối đến database!");
                return productList;
            }

            StringBuilder query = new StringBuilder("SELECT * FROM Products WHERE 1=1");

            // Lọc theo danh mục
            if (categoryId != null && !categoryId.isEmpty()) {
                query.append(" AND category_id = ?");
            }

            // Lọc theo khoảng giá
            if (priceRanges != null && priceRanges.length > 0) {
                query.append(" AND (");
                for (int i = 0; i < priceRanges.length; i++) {
                    if (i > 0) {
                        query.append(" OR ");
                    }
                    query.append("(price >= ? AND price <= ?)");
                }
                query.append(")");
            }

            // Lọc theo thương hiệu
            if (brands != null && brands.length > 0) {
                query.append(" AND brand_id IN (");
                for (int i = 0; i < brands.length; i++) {
                    if (i > 0) {
                        query.append(", ");
                    }
                    query.append("?");
                }
                query.append(")");
            }

            System.out.println("SQL Query: " + query.toString()); // Debug SQL

            ps = conn.prepareStatement(query.toString()); // Khởi tạo PreparedStatement

            // Gán giá trị vào các tham số của PreparedStatement
            int paramIndex = 1;
            if (categoryId != null && !categoryId.isEmpty()) {
                ps.setInt(paramIndex++, Integer.parseInt(categoryId));
            }

            if (priceRanges != null) {
                for (String range : priceRanges) {
                    String[] limits = range.split("-");
                    ps.setDouble(paramIndex++, Double.parseDouble(limits[0]));
                    ps.setDouble(paramIndex++, Double.parseDouble(limits[1]));
                }
            }

            if (brands != null) {
                for (String brand : brands) {
                    ps.setInt(paramIndex++, Integer.parseInt(brand));
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getString("status"));
                product.setBrandId(rs.getInt("brand_id"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setImage(rs.getString("image"));
                productList.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> searchProducts(String keyword) throws SQLException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }

        List<Product> productList = new ArrayList<>();
        // Tìm kiếm dựa trên product_name và description
        String sql = "SELECT * FROM Products WHERE product_name LIKE ? OR description LIKE ?";

        // Kiểm tra xem từ khóa có phải là số không (để áp dụng cho price, brand_id, category_id)
        boolean isNumeric = false;
        Double numericVal = null;
        try {
            numericVal = Double.parseDouble(keyword);
            isNumeric = true;
        } catch (NumberFormatException ex) {
            isNumeric = false;
        }

        if (isNumeric) {
            // Nếu từ khóa là số, thêm các điều kiện OR cho price, brand_id và category_id
            sql += " OR price = ? OR brand_id = ? OR category_id = ?";
        }

        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            int index = 1;
            pstmt.setString(index++, "%" + keyword + "%");
            pstmt.setString(index++, "%" + keyword + "%");
            if (isNumeric) {
                pstmt.setDouble(index++, numericVal);
                int intVal = numericVal.intValue();
                pstmt.setInt(index++, intVal);
                pstmt.setInt(index++, intVal);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("created_at");
                LocalDateTime createdAt = (ts != null) ? ts.toLocalDateTime() : null;
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("status"),
                        rs.getInt("brand_id"),
                        rs.getInt("category_id"),
                        rs.getString("image"),
                        createdAt
                );
                productList.add(product);
            }
        }
        return productList;
    }

    // Lấy danh sách sản phẩm theo phân trang
    public List<Product> getProductsPaginated(int offset, int pageSize) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products ORDER BY product_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, offset);
            pstmt.setInt(2, pageSize);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("created_at");
                LocalDateTime createdAt = (ts != null) ? ts.toLocalDateTime() : null;
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("status"),
                        rs.getInt("brand_id"),
                        rs.getInt("category_id"),
                        rs.getString("image"),
                        createdAt
                );
                products.add(product);
            }
        }
        return products;
    }

    // Lấy tổng số sản phẩm (để tính số trang)
    public int getProductsCount() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Products";
        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();

        // Giả sử lọc theo danh mục "Vợt cầu lông" (category_id = 1)
        String categoryId = "4";

        // Giả sử lọc theo khoảng giá: 500.000đ - 1.000.000đ
        String[] priceRanges = {"500000-1000000"};

        // Giả sử lọc theo thương hiệu: Yonex (brand_id = 2) và Lining (brand_id = 3)
        String[] brands = {"4"};

        // Gọi phương thức lọc sản phẩm
        List<Product> filteredProducts = dao.getFilteredProducts(categoryId, priceRanges, brands);

        // Kiểm tra và in kết quả
        if (filteredProducts.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm nào phù hợp!");
        } else {
            System.out.println("Danh sách sản phẩm được lọc:");
            for (Product p : filteredProducts) {
                System.out.println("ID: " + p.getProductId()
                        + ", Tên: " + p.getProductName()
                        + ", Giá: " + p.getPrice() + " VND"
                        + ", Thương hiệu ID: " + p.getBrandId()
                        + ", Danh mục ID: " + p.getCategoryId());
            }
        }

    }
}
