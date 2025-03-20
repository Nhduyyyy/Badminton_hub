
package controller;

import entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ProductService;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet(name="UpdateProductServlet", urlPatterns={"/updateProduct"})
public class UpdateProductServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String status = request.getParameter("status");
            int brandId = Integer.parseInt(request.getParameter("brandId"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            String image = request.getParameter("image");
            Product product = new Product(id, productName, description, price, stock, status, brandId, categoryId, image, LocalDateTime.now());
            
            ProductService productService = new ProductService();
            productService.updateProduct(product);
            
            response.sendRedirect("productlist?cId=" + categoryId);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
