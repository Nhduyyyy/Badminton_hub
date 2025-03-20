

package controller;

import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import services.lockUnlockUserService;


@WebServlet(name = "lockUnlockUser", urlPatterns = {"/lockUnlock"})
public class LockUnlockUser extends HttpServlet {

    private lockUnlockUserService userService  = new lockUnlockUserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action"); // "lock" hoặc "unlock"
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            if ("lock".equalsIgnoreCase(action)) {
                userService.lockUser(id);
            } else if ("unlock".equalsIgnoreCase(action)) {
                userService.unlockUser(id);
            }
            response.sendRedirect(request.getContextPath() + "/userManagement");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi cập nhật trạng thái người dùng");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}
