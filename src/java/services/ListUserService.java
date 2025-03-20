
package services;

import UserDAO.UserDAO;
import entity.User;
import java.util.List;


public class ListUserService {
    private UserDAO userDao = new UserDAO();

    // Lấy danh sách người dùng theo điều kiện tìm kiếm hoặc lọc và phân trang
    public List<User> getUsers(String query, String roleFilter, String statusFilter, int offset, int pageSize, Integer excludeUserId) {
        if (query != null && !query.trim().isEmpty()) {
            return userDao.searchUsers(query);
        } else {
            return userDao.filterUsersPaginated(roleFilter, statusFilter, offset, pageSize, excludeUserId);
        }
    }

    // Đếm tổng số người dùng theo điều kiện
    public int getTotalUsers(String query, String roleFilter, String statusFilter, Integer excludeUserId) {
        if (query != null && !query.trim().isEmpty()) {
            List<User> userList = userDao.searchUsers(query);
            return userList.size();
        } else {
            return userDao.countUsers(roleFilter, statusFilter, excludeUserId);
        }
    }
}