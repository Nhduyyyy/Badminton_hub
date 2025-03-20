package ProductDAO;

import context.DBContext;
import entity.Brand;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO {

    public void addBrand(Brand brand) throws SQLException {
        String sql = "INSERT INTO Brands (brand_name) VALUES (?)";
        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, brand.getBrandName());
            pstmt.executeUpdate();
        }
    }

    public Brand getBrandById(int id) throws SQLException {
        String sql = "SELECT * FROM Brands WHERE brand_id = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Brand(rs.getInt("brand_id"), rs.getString("brand_name"));
            }
        }
        return null;
    }

    public List<Brand> getAllBrands() throws SQLException {
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM Brands";
        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                brands.add(new Brand(rs.getInt("brand_id"), rs.getString("brand_name")));
            }
        }
        return brands;
    }

    public void updateBrand(Brand brand) throws SQLException {
        String sql = "UPDATE Brands SET brand_name = ? WHERE brand_id = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, brand.getBrandName());
            pstmt.setInt(2, brand.getBrandId());
            pstmt.executeUpdate();
        }
    }

    public void deleteBrand(int id) throws SQLException {
        String sql = "DELETE FROM Brands WHERE brand_id = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
