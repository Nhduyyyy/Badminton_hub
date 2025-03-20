
package services;


import UserDAO.UserDAO;
import entity.Role;
import entity.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;


public class updateUserService {
    private UserDAO userDao = new UserDAO();

    public User getUserById(int id) {
        for (User u : userDao.selectAllUsers()) {
            if (u.getUserId() == id) {
                // Nếu trùng khớp, trả về đối tượng user đó
                return u;
            }
        }
        // Nếu không tìm thấy user nào có id phù hợp, trả về null.
        return null;
    }
    
    
    public boolean updateUser(int id, String username, String email, String password,
                              String fullName, String phoneNumber, int roleId,
                              boolean locked, String avatar, String sex,
                              String birthDateStr, int score) throws SQLException {
        
        Date birthDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            birthDate = sdf.parse(birthDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            birthDate = new Date();
        }
        
        Role role;
        if (roleId == 1) {
            role = new Role(roleId, "Admin");
        } else if (roleId == 2) {
            role = new Role(roleId, "Manager");
        } else {
            role = new Role(roleId, "User");
        }
        
        // Nếu avatar rỗng (null hoặc chỉ chứa khoảng trắng), 
        // gán avatar mặc định là "default.gif"
        if (avatar == null || avatar.trim().isEmpty()) {
            avatar = "default.gif";
        }
        
        // Lấy thời gian hiện tại dưới dạng java.sql.Timestamp
        // để cập nhật thời điểm thay đổi thông tin user.
        java.sql.Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());
        
        // Tạo đối tượng user cập nhật
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
        
        // Gọi phương thức updateUser của UserDAO
        return userDao.updateUser(updatedUser);
    }
}
