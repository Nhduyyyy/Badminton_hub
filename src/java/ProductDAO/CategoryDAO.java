package ProductDAO;

import context.DBContext;
import entity.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public void addCategory(Category category) throws SQLException {
        String sql = "INSERT INTO Categories (category_name, created_at) VALUES (?, ?)";
        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, category.getcName());
            // Sử dụng timestamp nếu createdAt không null
            pstmt.setTimestamp(2, category.getCreatedAt() != null ? new Timestamp(category.getCreatedAt().getTime()) : null);
            pstmt.executeUpdate();
        }
    }

    public Category getCategoryById(int id) throws SQLException {
        String sql = "SELECT * FROM Categories WHERE category_id = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setcId(rs.getInt("category_id"));
                category.setcName(rs.getString("category_name"));
                category.setCreatedAt(rs.getTimestamp("created_at"));
                return category;
            }
        }
        return null;
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories";
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

    public void updateCategory(Category category) throws SQLException {
        String sql = "UPDATE Categories SET category_name = ? WHERE category_id = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, category.getcName());
            pstmt.setInt(2, category.getcId());
            pstmt.executeUpdate();
        }
    }

    public void deleteCategory(int id) throws SQLException {
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
