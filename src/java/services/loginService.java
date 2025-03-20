package services;

import LoginGoogle.UserGoogleDto;
import UserDAO.UserDAO;
import entity.Role;
import entity.User;
import java.sql.Timestamp;
import java.sql.SQLException;

public class loginService {

    private UserDAO userDao = new UserDAO();

    // Phương thức xác thực đăng nhập
    public User authenticate(String identifier, String password) {
        return userDao.authenticateUser(identifier, password);
    }

    // Phương thức đăng nhập bằng Google
    public User authenticateGoogle(UserGoogleDto googleUser) {
        // Tìm kiếm người dùng theo email từ Google
        User user = userDao.findByEmail(googleUser.getEmail());
        if (user == null) {
            // Kiểm tra Role mặc định: Lưu ý xác nhận lại RoleID trong DB của bạn.
            Role defaultRole = new Role(3, "User", "Nguoi dung thong thuong");
            user = new User();
            user.setEmail(googleUser.getEmail());
            user.setUsername(googleUser.getEmail()); // hoặc có thể dùng googleUser.getName()
            user.setFullName(googleUser.getName());
            user.setAvatar(googleUser.getPicture());
            user.setPassword(""); // Với Google, có thể để trống mật khẩu
            user.setRole(defaultRole);

            // Thiết lập các thông tin mặc định khác
            user.setPhoneNumber("");             // Số điện thoại rỗng
            user.setScore(0);                    // Điểm mặc định là 0
            user.setSex("Unknown");              // Giới tính mặc định
            user.setBirthDate(new java.util.Date()); // Set ngày sinh là ngày hiện tại
            user.setLocked(false);               // Mặc định tài khoản không bị khóa

            try {
                userDao.insertUser(user);
                // Sau khi insert, lấy lại thông tin người dùng mới tạo
                user = userDao.findByEmail(googleUser.getEmail());
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return user;
    }

    // Tìm user theo email
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    // Cập nhật resetToken và thời gian hết hạn
    public void updateResetToken(String email, String token) {
        userDao.updateResetToken(email, token);
    }

    // Tìm user theo resetToken
    public User findUserByResetToken(String token) {
        return userDao.findUserByResetToken(token);
    }

    // Cập nhật mật khẩu mới dựa trên token
    public boolean updatePasswordByToken(String token, String newPassword) {
        return userDao.updatePasswordByToken(token, newPassword);
    }
    
//    public User checkLogin(String username, String password) {
//        return userDao.checkLogin(username, password);
//    }

}
