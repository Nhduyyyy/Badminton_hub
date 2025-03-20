package controller;


import entity.Order;
import entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.OrderService;


@WebServlet(name = "OrderHistoryServlet", urlPatterns = {"/orderhistory"})
public class OrderHistoryServlet extends HttpServlet {
    private OrderService orderService;
    
    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");
            
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            int userId = user.getUserId();
            List<Order> orders = orderService.getOrdersByUserId(userId);
            
            if (orders == null || orders.isEmpty()) {
                System.out.println("No order details found for user: " + userId);
            } else {
                System.out.println("Found " + orders.size() + " order details for user: " + userId);
            }
            
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/Cart/orderHistory.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(OrderHistoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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