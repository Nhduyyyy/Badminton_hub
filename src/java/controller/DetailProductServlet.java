/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import ProductDAO.ProductDAO;
import UserDAO.UserDAO;
import entity.Brand;
import entity.Category;
import entity.Product;
import entity.Review;
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
 * @author PC Windows 10
 */
@WebServlet(name = "DetailProductServlet", urlPatterns = {"/productdetail"})
public class DetailProductServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            int ID = Integer.parseInt(request.getParameter("productId"));
            ProductDAO productDAO = new ProductDAO();
            Brand b = productDAO.getBrandByID(ID);
            request.setAttribute("brand", b);
              UserDAO userDAO = new UserDAO();
    List<Review> reviews = userDAO.getReviewsByProductId(ID);
            Product p = productDAO.selectProduct(ID);
            request.setAttribute("product", p);
             request.setAttribute("reviews", reviews);
            List<Category> listC = productDAO.getAllCategories();
            request.setAttribute("listC", listC);

            request.getRequestDispatcher("Product/productDetail.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(DetailProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
