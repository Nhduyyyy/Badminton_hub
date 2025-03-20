
package controller;


import UserDAO.UserDAO;
import entity.Role;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(name = "createUserServlet", urlPatterns = {"/createUser"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class CreateUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward tới trang createUser.jsp
        request.getRequestDispatcher("/user/createUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu text từ form
        String username = request.getParameter("username").trim();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName").trim();
        String phoneNumber = request.getParameter("phoneNumber").trim();
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        boolean locked = Boolean.parseBoolean(request.getParameter("locked"));
        String sex = request.getParameter("sex");
        String birthDateStr = request.getParameter("birthDate");
        int score = Integer.parseInt(request.getParameter("score"));

        // Xử lý ngày sinh
        Date birthDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            birthDate = sdf.parse(birthDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            birthDate = new Date();
        }

        // Xác định vai trò dựa trên roleId
        Role role;
        if (roleId == 1) {
            role = new Role(roleId, "Admin");
        } else if (roleId == 2) {
            role = new Role(roleId, "Manager");
        } else {
            role = new Role(roleId, "User");
        }

        // -------- BẮT ĐẦU XỬ LÝ FILE ẢNH (avatar) --------
        // Lấy file upload từ form (tên trường phải trùng với <input type="file" name="avatar" />)
        Part filePart = request.getPart("avatar");
        // Đường dẫn mặc định nếu không upload ảnh
        String avatarPath = "default.gif";  // Hoặc đường dẫn mặc định khác

        if (filePart != null && filePart.getSize() > 0) {
            // Lấy tên file gốc một cách an toàn
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // Xác định đường dẫn tuyệt đối tới thư mục "Image" trong webapp
            // (nằm trong "Web Pages/Image")
            String uploadPath = getServletContext().getRealPath("/Image");

            // Tạo thư mục nếu chưa tồn tại
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Ghi file vào thư mục "Image"
            filePart.write(uploadPath + File.separator + fileName);

            // Lưu lại đường dẫn ảnh (tương đối) để hiển thị trong ứng dụng
            avatarPath = "Image/" + fileName;
        }
        // -------- KẾT THÚC XỬ LÝ FILE ẢNH --------

        // Lấy thời gian hiện tại cho các trường createdAt và updatedAt
        java.sql.Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());

        // Tạo đối tượng User và thiết lập các thuộc tính
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFullName(fullName);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setRole(role);
        newUser.setAvatar(avatarPath);  // Lưu đường dẫn ảnh đã upload
        newUser.setScore(score);
        newUser.setSex(sex);
        newUser.setBirthDate(birthDate);
        newUser.setCreatedAt(currentTime);
        newUser.setUpdatedAt(currentTime);
        newUser.setLocked(locked);

        // Thực hiện chèn người dùng mới vào database
        try {
            UserDAO userDao = new UserDAO();
            userDao.insertUser(newUser);
            // Nếu tạo người dùng thành công, chuyển hướng sang trang quản lý người dùng
            response.sendRedirect(request.getContextPath() + "/userManagement");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessages", "Tạo người dùng thất bại: " + e.getMessage());
            request.getRequestDispatcher("/user/createUser.jsp").forward(request, response);
        }
    }
}
