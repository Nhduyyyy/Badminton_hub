/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(name = "PaymentConfirmServlet", urlPatterns = {"/paymentconfirm"})
public class PaymentConfirmServlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("/badminton/home.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String orderId = (String) session.getAttribute("orderId");
        
        if (orderId == null) {
            request.setAttribute("error", "Lỗi! Không tìm thấy đơn hàng. Vui lòng thử lại.");
            request.getRequestDispatcher("/Cart/cart.jsp").forward(request, response);
            return;
        }
        session.removeAttribute("qrCodeUrl");
        session.removeAttribute("totalAmount");
        session.removeAttribute("orderId"); 
        
        request.setAttribute("message", "Thanh toán thành công! Đơn hàng của bạn đang được xử lý.");
        request.getRequestDispatcher("/Cart/orderSuccess.jsp").forward(request, response);
    }


}
