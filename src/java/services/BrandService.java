package services;

import ProductDAO.BrandDAO;
import entity.Brand;
import java.sql.SQLException;
import java.util.List;

public class BrandService {

    private final BrandDAO brandDAO;

    public BrandService() {
        this.brandDAO = new BrandDAO();
    }

    public void addBrand(Brand brand) throws SQLException {
        brandDAO.addBrand(brand);
    }

    public Brand getBrandById(int id) throws SQLException {
        return brandDAO.getBrandById(id);
    }

    public List<Brand> getAllBrands() throws SQLException {
        return brandDAO.getAllBrands();
    }

    public void updateBrand(Brand brand) throws SQLException {
        brandDAO.updateBrand(brand);
    }

    public void deleteBrand(int id) throws SQLException {
        brandDAO.deleteBrand(id);
    }
}
