package controller;

import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import services.updateUserService;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ProfileServlet extends HttpServlet {

    private updateUserService updateService = new updateUserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin người dùng từ session
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // Đẩy thông tin profile lên request và forward đến trang profile.jsp
        request.setAttribute("profile", currentUser);
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin người dùng từ session
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy dữ liệu cơ bản từ form
        int id = currentUser.getUserId();
        String username = request.getParameter("username").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        String fullName = request.getParameter("fullName").trim();
        String phoneNumber = request.getParameter("phoneNumber").trim();
        String sex = request.getParameter("sex").trim();
        String birthDateStr = request.getParameter("birthDate").trim();

        // Xử lý upload avatar (nếu có)
        Part filePart = request.getPart("avatar");
        String avatar = currentUser.getAvatar(); // dùng avatar hiện tại nếu không upload mới
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("/Image");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            filePart.write(uploadPath + File.separator + fileName);
            avatar = "Image/" + fileName;
        }

        // Phân biệt tài khoản admin và user thông qua roleName
        boolean isAdmin = currentUser.getRole().getRoleName().equalsIgnoreCase("Admin");
        int roleId = currentUser.getRole().getRoleId(); // giữ nguyên role hiện tại
        boolean locked;
        int score;

        if (isAdmin) {
            // Admin được cập nhật các trường đặc biệt
            locked = Boolean.parseBoolean(request.getParameter("locked"));
            try {
                score = Integer.parseInt(request.getParameter("score"));

            } catch (NumberFormatException e) {
                score = currentUser.getScore();
            }
        } else {
            // User thường giữ nguyên giá trị của score và locked
            locked = currentUser.isLocked();
            score = currentUser.getScore();
        }

        try {
            boolean success = updateService.updateUser(id, username, email, password, fullName, phoneNumber, roleId, locked, avatar, sex, birthDateStr, score);
            if (success) {
                // Sau cập nhật, lấy lại thông tin người dùng từ DB
                User updatedUser = updateService.getUserById(id);
                // Cập nhật lại session và attribute "profile"
                request.getSession().setAttribute("user", updatedUser);
                request.setAttribute("profile", updatedUser);
                request.setAttribute("successMessage", "Cập nhật profile thành công.");
            } else {
                request.setAttribute("profile", currentUser);
                request.setAttribute("errorMessage", "Cập nhật profile thất bại.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("profile", currentUser);
            request.setAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
        }
        // Sau khi xử lý, forward trở lại trang profile.jsp
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }
}
