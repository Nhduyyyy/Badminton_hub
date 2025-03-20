
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ProductService;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="DeleteProductServlet", urlPatterns={"/deleteProduct"})
public class DeleteProductServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            ProductService productService = new ProductService();
            productService.deleteProduct(id);
            response.sendRedirect("productlist?cId=" + categoryId);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
