/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import ProductDAO.ProductDAO;
import entity.Brand;
import entity.Category;
import entity.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "AddProductServlet", urlPatterns = {"/addproduct"})
public class AddProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Brand> brandList = productDAO.getAllBrands();
            List<Category> categoryList = productDAO.getAllCategories();

            if (brandList == null || categoryList == null) {
                request.setAttribute("errorMessage", "Không thể lấy dữ liệu từ cơ sở dữ liệu.");
                request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
                return;
            }

            request.setAttribute("brandList", brandList);
            request.setAttribute("categoryList", categoryList);
            request.getRequestDispatcher("/Product/addProduct.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi lấy dữ liệu từ cơ sở dữ liệu.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if ("add".equals(action)) {
                addProduct(request, response);
            } else {
                response.sendRedirect("errorPage.jsp");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Lỗi khi thêm sản phẩm.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String status = request.getParameter("status");
        String brandName = request.getParameter("brand_name");
        String categoryName = request.getParameter("category_name");
        String image = request.getParameter("image");

        // Lấy brandId và categoryId từ tên Brand và Category
        int brandId = productDAO.getBrandIdByName(brandName);
        int categoryId = productDAO.getCategoryIdByName(categoryName);

        if (brandId == -1 || categoryId == -1) {
            request.setAttribute("errorMessage", "Thương hiệu hoặc loại sản phẩm không tồn tại.");
            request.getRequestDispatcher("/Product/addProduct.jsp").forward(request, response);
            return;
        }

        // Tạo đối tượng sản phẩm và thêm vào CSDL
        Product product = new Product(name, description, price, stock, status, brandId, categoryId, image);
        productDAO.addProduct(product);

        // Sau khi thêm thành công, chuyển hướng về danh sách sản phẩm
        response.sendRedirect(request.getContextPath() + "/badminton/productlist");
    }
}

