
package services;



import UserDAO.UserDAO;
import entity.User;
import java.sql.SQLException;


public class lockUnlockUserService {
    private UserDAO userDao = new UserDAO();

    // Hàm khóa người dùng
    public void lockUser(int id) throws SQLException {
        User user = userDao.findById(id);
        if (user != null) {
            user.setLocked(true);
            userDao.updateUser(user);
        }
    }

    // Hàm mở khóa người dùng
    public void unlockUser(int id) throws SQLException {
        User user = userDao.findById(id);
        if (user != null) {
            user.setLocked(false);
            userDao.updateUser(user);
        }
    }
}
