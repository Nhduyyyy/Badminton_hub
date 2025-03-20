
package services;


import UserDAO.UserDAO;
import entity.Role;
import entity.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.Date;



public class createUserService {
    
    private UserDAO userDao = new UserDAO();

    
    public void createUser(String username, String email, String password,
                              String fullName, String phoneNumber, int roleId,
                              boolean locked, String avatar, String sex,
                              String birthDateStr, int score) throws SQLException {

        Date birthDate;
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
        
        if (avatar == null || avatar.trim().isEmpty()) {
            avatar = "default.gif";
        }
        
        java.sql.Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFullName(fullName);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setRole(role);
        newUser.setAvatar(avatar);
        newUser.setScore(score);
        newUser.setSex(sex);
        newUser.setBirthDate(birthDate);
        newUser.setCreatedAt(currentTime);
        newUser.setUpdatedAt(currentTime);
        newUser.setLocked(locked);
        
        // Gọi hàm insertUser của UserDAO (kiểu void)
        userDao.insertUser(newUser);
    }
}
