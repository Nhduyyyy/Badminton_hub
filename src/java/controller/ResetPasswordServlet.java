package controller;

import entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.loginService;

@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/resetPassword"})
public class ResetPasswordServlet extends HttpServlet {
    
    private loginService loginService = new loginService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        if(token == null || token.isEmpty()){
            response.sendRedirect("login");
            return;
        }
        // Kiểm tra xem token có hợp lệ hay không
        User user = loginService.findUserByResetToken(token);
        if(user == null){
            request.setAttribute("errorMessage", "Token không hợp lệ hoặc đã hết hạn.");
        } else {
            request.setAttribute("token", token);
        }
        request.getRequestDispatcher("/authenticator_authorization/resetPassword.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if(newPassword == null || !newPassword.equals(confirmPassword)){
            request.setAttribute("errorMessage", "Mật khẩu không khớp.");
            request.setAttribute("token", token);
            request.getRequestDispatcher("/authenticator_authorization/resetPassword.jsp").forward(request, response);
            return;
        }
        
        // Cập nhật mật khẩu dựa trên token và xóa token sau khi cập nhật thành công
        boolean updated = loginService.updatePasswordByToken(token, newPassword);
        if(updated){
            request.setAttribute("message", "Mật khẩu đã được thay đổi thành công.");
            request.getRequestDispatcher("/authenticator_authorization/login.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Token không hợp lệ hoặc đã hết hạn.");
            request.getRequestDispatcher("/authenticator_authorization/resetPassword.jsp").forward(request, response);
        }
    }
}
