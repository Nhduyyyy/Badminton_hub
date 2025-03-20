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
import java.util.regex.Pattern;

@WebServlet(name = "registrationServlet", urlPatterns = {"/register"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward đến trang đăng ký
        request.getRequestDispatcher("/authenticator_authorization/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ----- LẤY DỮ LIỆU TEXT TỪ FORM -----
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        String fullName = request.getParameter("fullName");
        String sex = request.getParameter("sex");
        String birthDateStr = request.getParameter("birthDate");
  // Kiểm tra email và số điện thoại hợp lệ
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String phoneRegex = "^\\d{10}$"; // Chỉ chấp nhận đúng 11 số

        if (!Pattern.matches(emailRegex, email)) {
            request.setAttribute("errorMessage", "Email không hợp lệ!");
            request.getRequestDispatcher("/authenticator_authorization/register.jsp").forward(request, response);
            return;
        }

        if (!Pattern.matches(phoneRegex, phoneNumber)) {
            request.setAttribute("errorMessage", "Số điện thoại phải có đúng 11 số!");
            request.getRequestDispatcher("/authenticator_authorization/register.jsp").forward(request, response);
            return;
        }
        // Xử lý ngày sinh
        Date birthDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            birthDate = sdf.parse(birthDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            birthDate = new Date(); // Nếu parse lỗi, gán ngày hiện tại
        }

        // Role mặc định cho người dùng mới
        Role role = new Role(3, "User");

        // Thiết lập giá trị mặc định
        String avatar = "default.gif";  // Nếu không upload ảnh
        int score = 0;
        boolean locked = false;

        // ----- XỬ LÝ UPLOAD ẢNH -----
        Part filePart = request.getPart("avatar"); // Lấy file (nếu có) từ form

        if (filePart != null && filePart.getSize() > 0) {
            // Lấy tên file một cách an toàn
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // Lấy đường dẫn tuyệt đối đến thư mục "Image" trong webapp
            String uploadPath = getServletContext().getRealPath("/Image");
// Tạo thư mục nếu chưa tồn tại
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Ghi file lên server
            filePart.write(uploadPath + File.separator + fileName);

            // Gán đường dẫn avatar để lưu trong DB
            avatar = "Image/" + fileName;
        }

        // Lấy thời gian hiện tại
        java.sql.Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());

        // ----- TẠO ĐỐI TƯỢNG USER -----
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setRole(role);
        user.setPhoneNumber(phoneNumber);
        user.setAvatar(avatar);
        user.setScore(score);
        user.setSex(sex);
        user.setBirthDate(birthDate);
        user.setCreatedAt(currentTime);
        user.setUpdatedAt(currentTime);
        user.setLocked(locked);

        try {
            // Gọi UserDAO để chèn người dùng vào DB
            UserDAO userDao = new UserDAO();
            userDao.insertUser(user);

            // Nếu đăng ký thành công -> chuyển hướng sang trang đăng nhập
            request.setAttribute("successMessage", "Đăng kí thành công, vui lòng đăng nhập.");
            request.getRequestDispatcher("/authenticator_authorization/login.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // Nếu có lỗi -> hiển thị thông báo lỗi và quay lại trang đăng ký
            request.setAttribute("errorMessage", "Đăng kí thất bại: " + e.getMessage());
            request.getRequestDispatcher("/authenticator_authorization/register.jsp").forward(request, response);
        }
    }
}