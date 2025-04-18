/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import ProductDAO.ProductDAO;
import entity.Product;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name="SearchProAjax", urlPatterns={"/searchA"})
public class SearchProAjax extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String  search= request.getParameter("query");
            ProductDAO dao=new ProductDAO();
            List<Product> list = null;
            
            
            list = dao.searchProductByName(search);
            
            
            PrintWriter out = response.getWriter();
            for(Product o : list){
                out.println("  <div class=\"product-item\">\n" +
                        "                          <a href=\"/badminton/productdetail?productId="+o.getProductId()+"\" >\n" +
                                "                        <img src=\""+o.getImage()+"\" alt=\""+o.getProductName()+"\">\n" +"</a>\n"+
                                        "                        <p>"+o.getProductName()+"</p>\n" +
                                                "                        <p class=\"price\">"+o.getPrice()+" đ</p>\n" +
                                                        "                    </div>");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchProAjax.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
