
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

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
import services.ProductService;

/**
 *
 * @author PC Windows 10
 */
@WebServlet(name="ProductServlet", urlPatterns={"/productlist"})
    public class ProductListServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            int cateID = Integer.parseInt(request.getParameter("cId"));
            ProductService productService = new ProductService();
            List<Product> listP = (List)productService.getProductsByCategoryId(cateID);
            List<Category> listC = productService.getAllCategories();
            request.getSession().setAttribute("listC", listC);
            request.getSession().setAttribute("listP", listP);
            request.getRequestDispatcher("Product/productList.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProductListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

}
