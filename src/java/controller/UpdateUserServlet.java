
package controller;


import UserDAO.UserDAO;
import entity.Role;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "updateUserServlet", urlPatterns = {"/updateUser"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,        // 10MB
    maxRequestSize = 1024 * 1024 * 50       // 50MB
)
public class UpdateUserServlet extends HttpServlet {

    private UserDAO userDao = new UserDAO();

    // Phương thức nội bộ để lấy người dùng theo id
    private User getUserById(int id) {
        // Duyệt qua danh sách người dùng từ DAO
        for (User u : userDao.selectAllUsers()) {
            if (u.getUserId() == id) {
                return u;
            }
        }
        return null;
    }
    
    // Phương thức nội bộ để cập nhật người dùng
    private boolean updateUser(int id, String username, String email, String password,
                               String fullName, String phoneNumber, int roleId,
                               boolean locked, String avatar, String sex,
                               String birthDateStr, int score) throws SQLException {
        
        // Xử lý định dạng ngày sinh
        Date birthDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            birthDate = sdf.parse(birthDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            birthDate = new Date();
        }
        
        // Xác định role dựa trên roleId
        Role role;
        if (roleId == 1) {
            role = new Role(roleId, "Admin");
        } else if (roleId == 2) {
            role = new Role(roleId, "Manager");
        } else {
            role = new Role(roleId, "User");
        }
        
        // Nếu avatar không được cung cấp, gán giá trị mặc định
        if (avatar == null || avatar.trim().isEmpty()) {
            avatar = "default.gif";
        }
        
        // Lấy thời gian hiện tại
        java.sql.Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());
        
        // Tạo đối tượng User cập nhật và thiết lập các thuộc tính
        User updatedUser = new User();
        updatedUser.setUserId(id);
        updatedUser.setUsername(username);
        updatedUser.setEmail(email);
        updatedUser.setPassword(password);
        updatedUser.setFullName(fullName);
        updatedUser.setRole(role);
        updatedUser.setPhoneNumber(phoneNumber);
        updatedUser.setAvatar(avatar);
        updatedUser.setScore(score);
        updatedUser.setSex(sex);
        updatedUser.setBirthDate(birthDate);
        updatedUser.setUpdatedAt(currentTime);
        updatedUser.setLocked(locked);
        
        // Gọi phương thức updateUser của DAO và trả về kết quả
        return userDao.updateUser(updatedUser);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy id người dùng cần cập nhật từ request
        int id = Integer.parseInt(request.getParameter("userId"));
        
        // Lấy thông tin người dùng và đưa lên trang cập nhật
        User updateUser = getUserById(id);
        request.setAttribute("updateUser", updateUser);
        request.getRequestDispatcher("/user/updateUser.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form cập nhật
        int id = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username").trim();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName").trim();
        String phoneNumber = request.getParameter("phoneNumber").trim();
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        boolean locked = Boolean.parseBoolean(request.getParameter("locked"));
        
        // Lấy avatar hiện tại từ trường ẩn trong form (nếu có)
        String avatar = request.getParameter("Avatar");
        String sex = request.getParameter("sex");
        String birthDateStr = request.getParameter("birthDate");
        int score = Integer.parseInt(request.getParameter("score"));
        
        // ----- XỬ LÝ UPLOAD ẢNH -----
        // Lấy file upload từ form (input type="file" có name="avatar")
        Part filePart = request.getPart("avatar");
        if (filePart != null && filePart.getSize() > 0) {
            // Lấy tên file an toàn
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            // Lấy đường dẫn thực của thư mục "Image" trong webapp
            String uploadPath = getServletContext().getRealPath("/Image");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // Ghi file vào thư mục
            filePart.write(uploadPath + File.separator + fileName);
            // Gán lại avatar với đường dẫn tương đối
            avatar = "Image/" + fileName;
        }
        // ----- KẾT THÚC XỬ LÝ ẢNH -----
        
        try {
            boolean success = updateUser(id, username, email, password, fullName,
                    phoneNumber, roleId, locked, avatar, sex, birthDateStr, score);
            if (success) {
                // Nếu cập nhật thành công, chuyển hướng về trang quản lý người dùng
                response.sendRedirect(request.getContextPath() + "/userManagement");
            } else {
                // Nếu cập nhật thất bại, chuyển hướng về trang cập nhật với thông báo lỗi
                request.setAttribute("errorMessage", "Cập nhật người dùng thất bại.");
                request.setAttribute("user", getUserById(id));
                request.getRequestDispatcher("/updateUser").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Cập nhật người dùng thất bại: " + e.getMessage());
            request.setAttribute("user", getUserById(id));
            request.getRequestDispatcher("/updateUser").forward(request, response);
        }
    }
}
