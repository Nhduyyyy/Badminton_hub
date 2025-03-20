
package controller;

import entity.Brand;
import entity.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ProductService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="NewProductServlet", urlPatterns={"/newProduct"})
public class NewProductServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            ProductService productService = new ProductService();
            List<Category> listC = productService.getAllCategories();
            List<Brand> listB = productService.getAllBrands();
            request.setAttribute("listC", listC);
            request.setAttribute("listB", listB);
            request.getRequestDispatcher("Product/productForm.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
