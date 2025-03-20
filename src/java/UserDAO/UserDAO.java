/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserDAO;

import context.DBContext;
import entity.Review;
import entity.Role;
import entity.User;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    @Override
    public User authenticateUser(String identifier, String password) {

        User user = null;

        String sql = "SELECT u.UserID, u.Username, u.Email, u.Password, u.FullName, "
                + "       u.PhoneNumber, u.Avatar, u.Score, u.Sex, "
                + "       u.BirthDate, u.CreatedAt, u.UpdatedAt, u.Locked, "
                + "       r.RoleID AS RoleId, r.RoleName, r.Description "
                + "FROM [Users] u "
                + "INNER JOIN Roles r ON u.RoleId = r.RoleID "
                + "WHERE (u.Username = ? OR u.Email = ? OR u.PhoneNumber = ?) AND u.password = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, identifier);
            stmt.setString(2, identifier);
            stmt.setString(3, identifier);
            stmt.setString(4, password);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Role role = new Role(rs.getInt("RoleId"),
                            rs.getString("RoleName"),
                            rs.getString("Description"));

                    user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setRole(role);
                    user.setUsername(rs.getString("Username"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setFullName(rs.getString("FullName"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setAvatar(rs.getString("Avatar"));
                    user.setScore(rs.getInt("Score"));
                    user.setSex(rs.getString("Sex"));
                    user.setBirthDate(rs.getDate("BirthDate"));
                    user.setCreatedAt(rs.getTimestamp("createdAt"));
                    user.setUpdatedAt(rs.getTimestamp("updatedAt"));
                    user.setLocked(rs.getBoolean("Locked"));
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void insertUser(User user) throws SQLException {

        String sql = "INSERT INTO [Users] (Username, Email, Password, FullName, RoleId, PhoneNumber, "
                + "Avatar, Score, Sex, BirthDate, CreatedAt, UpdatedAt, Locked) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());

            stmt.setString(2, user.getEmail());

            stmt.setString(3, user.getPassword());

            stmt.setString(4, user.getFullName());

            stmt.setInt(5, user.getRole().getRoleId());

            stmt.setString(6, user.getPhoneNumber());

            stmt.setString(7, user.getAvatar());

            stmt.setInt(8, user.getScore());

            stmt.setString(9, user.getSex());

            stmt.setDate(10, new java.sql.Date(user.getBirthDate().getTime()));

            stmt.setBoolean(11, user.isLocked());

            int affectedRows = stmt.executeUpdate();
            System.out.println("Insert affected rows: " + affectedRows);

        }
    }

    @Override
    public List<User> selectAllUsers() {

        List<User> userList = new ArrayList<>();

        String sql = " SELECT u.UserID, u.Username, u.Email, u.Password, u.FullName, "
                + "       u.PhoneNumber, u.Avatar, u.Score, u.Sex, "
                + "       u.BirthDate, u.CreatedAt, u.UpdatedAt, u.Locked, "
                + "       r.RoleID AS RoleId, r.RoleName, r.Description "
                + "FROM [Users] u "
                + "INNER JOIN Roles r ON u.RoleId = r.RoleID ";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Role role = new Role(rs.getInt("roleId"),
                        rs.getString("roleName"),
                        rs.getString("Description")
                );
                User user = new User();

                user.setUserId(rs.getInt("UserID"));
                user.setRole(role);
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullName(rs.getString("FullName"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setAvatar(rs.getString("Avatar"));
                user.setScore(rs.getInt("Score"));
                user.setSex(rs.getString("Sex"));
                user.setBirthDate(rs.getDate("BirthDate"));
                user.setCreatedAt(rs.getTimestamp("createdAt"));
                user.setUpdatedAt(rs.getTimestamp("updatedAt"));
                user.setLocked(rs.getBoolean("Locked"));

                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User findById(int id) throws SQLException {

        User user = null;

        String sql = " SELECT u.UserID, u.Username, u.Email, u.Password, u.FullName, "
                + "       u.PhoneNumber, u.Avatar, u.Score, u.Sex, "
                + "       u.BirthDate, u.CreatedAt, u.UpdatedAt, u.Locked, "
                + "       r.RoleID AS RoleId, r.RoleName, r.Description "
                + "FROM [Users] u "
                + "INNER JOIN Roles r ON u.RoleId = r.RoleID WHERE UserID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    Role role = new Role(rs.getInt("roleId"),
                            rs.getString("roleName"),
                            rs.getString("Description")
                    );
                    user = new User();

                    user.setUserId(rs.getInt("UserID"));
                    user.setRole(role);
                    user.setUsername(rs.getString("Username"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setFullName(rs.getString("FullName"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setAvatar(rs.getString("Avatar"));
                    user.setScore(rs.getInt("Score"));
                    user.setSex(rs.getString("Sex"));
                    user.setBirthDate(rs.getDate("BirthDate"));
                    user.setCreatedAt(rs.getTimestamp("createdAt"));
                    user.setUpdatedAt(rs.getTimestamp("updatedAt"));
                    user.setLocked(rs.getBoolean("Locked"));
                }
            }
        }
        return user;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM [Users] WHERE UserID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE [Users] SET Username = ?, Email = ?, Password = ?, FullName = ?, RoleId = ?, PhoneNumber = ?, UpdatedAt = GETDATE(), Locked = ?, Avatar = ?, Score = ?, Sex = ?, BirthDate = ?  WHERE UserID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getFullName());
            stmt.setInt(5, user.getRole().getRoleId());
            stmt.setString(6, user.getPhoneNumber());
            stmt.setBoolean(7, user.isLocked());
            stmt.setString(8, user.getAvatar());
            stmt.setInt(9, user.getScore());
            stmt.setString(10, user.getSex());
            stmt.setDate(11, new java.sql.Date(user.getBirthDate().getTime()));
            stmt.setInt(12, user.getUserId());

            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<User> searchUsers(String query) {

        List<User> userList = new ArrayList<>();

        String sql = "SELECT u.UserID, u.Username, u.Email, u.Password, u.FullName, "
                + "       u.PhoneNumber, u.Avatar, u.Score, u.Sex, "
                + "       u.BirthDate, u.CreatedAt, u.UpdatedAt, u.Locked, "
                + "       r.RoleID AS RoleId, r.RoleName, r.Description "
                + "FROM [Users] u "
                + "INNER JOIN Roles r ON u.RoleId = r.RoleID "
                + "WHERE u.username LIKE ? OR u.email LIKE ? OR u.fullName LIKE ? OR u.phoneNumber LIKE ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String likeQuery = "%" + query + "%";

            stmt.setString(1, likeQuery);
            stmt.setString(2, likeQuery);
            stmt.setString(3, likeQuery);
            stmt.setString(4, likeQuery);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Role role = new Role(rs.getInt("RoleID"),
                            rs.getString("RoleName"),
                            rs.getString("Description"));

                    User user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setRole(role);
                    user.setUsername(rs.getString("Username"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setFullName(rs.getString("FullName"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setAvatar(rs.getString("Avatar"));
                    user.setScore(rs.getInt("Score"));
                    user.setSex(rs.getString("Sex"));
                    user.setBirthDate(rs.getDate("BirthDate"));
                    user.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    user.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                    user.setLocked(rs.getBoolean("Locked"));

                    userList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    @Override
    public List<User> filterUsersPaginated(String roleFilter, String statusFilter, int offset, int pageSize, Integer excludeUserId) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT u.UserID, u.Username, u.Email, u.Password, u.FullName, " + "       u.PhoneNumber, u.CreatedAt, u.UpdatedAt, u.BirthDate,"
                + "u.Locked, u.Avatar, "
                + "       r.RoleID AS RoleId, r.RoleName "
                + "FROM [Users] u INNER JOIN Roles r ON u.RoleId = r.RoleID ";

        List<String> conditions = new ArrayList<>();
        if (!"all".equalsIgnoreCase(roleFilter)) {
            conditions.add("r.RoleName = ?");
        }
        if (!"all".equalsIgnoreCase(statusFilter)) {
            if ("active".equalsIgnoreCase(statusFilter)) {
                conditions.add("u.Locked = 0");
            } else if ("locked".equalsIgnoreCase(statusFilter)) {
                conditions.add("u.Locked = 1");
            }
        }
        if (excludeUserId != null) {
            conditions.add("u.UserID <> ?");
        }

        if (!conditions.isEmpty()) {
            sql += "WHERE " + String.join(" AND ", conditions) + " ";
        }

        sql += "ORDER BY \n"
                + "    CASE \n"
                + "        WHEN r.RoleName = 'admin' THEN 0 \n"
                + "        WHEN r.RoleName = 'manager' THEN 1 \n"
                + "        WHEN r.RoleName = 'user' THEN 2 \n"
                + "        ELSE 3 \n"
                + "    END, \n"
                + "    CASE WHEN u.Locked = 0 THEN 0 ELSE 1 END, \n"
                + "    u.Username ASC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            int index = 1;
            if (!"all".equalsIgnoreCase(roleFilter)) {
                stmt.setString(index++, roleFilter);
            }
            if (excludeUserId != null) {
                stmt.setInt(index++, excludeUserId);
            }
            stmt.setInt(index++, offset);
            stmt.setInt(index++, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Role role = new Role(rs.getInt("RoleId"), rs.getString("RoleName"));
                    User user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setFullName(rs.getString("FullName"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setBirthDate(rs.getDate("BirthDate"));
                    user.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    user.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                    user.setLocked(rs.getBoolean("Locked"));
                    user.setAvatar(rs.getString("Avatar"));
                    user.setRole(role);
                    userList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public int countUsers(String roleFilter, String statusFilter, Integer excludeUserId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM [Users] u INNER JOIN Roles r ON u.RoleId = r.RoleID ";

        List<String> conditions = new ArrayList<>();
        if (!"all".equalsIgnoreCase(roleFilter)) {
            conditions.add("r.RoleName = ?");
        }
        if (!"all".equalsIgnoreCase(statusFilter)) {
            if ("active".equalsIgnoreCase(statusFilter)) {
                conditions.add("u.Locked = 0");
            } else if ("locked".equalsIgnoreCase(statusFilter)) {
                conditions.add("u.Locked = 1");
            }
        }
        if (excludeUserId != null) {
            conditions.add("u.UserID <> ?");
        }

        if (!conditions.isEmpty()) {
            sql += "WHERE " + String.join(" AND ", conditions);
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            if (!"all".equalsIgnoreCase(roleFilter)) {
                stmt.setString(index++, roleFilter);
            }
            if (excludeUserId != null) {
                stmt.setInt(index++, excludeUserId);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public User findByEmail(String email) {

        User user = null;
        String sql = "SELECT u.UserID, u.Username, u.Email, u.Password, u.FullName, "
                + "u.PhoneNumber, u.Avatar, u.Score, u.Sex, "
                + "u.BirthDate, u.CreatedAt, u.UpdatedAt, u.Locked, "
                + "r.RoleID AS RoleId, r.RoleName, r.Description "
                + "FROM Users u "
                + "INNER JOIN Roles r ON u.RoleId = r.RoleID "
                + "WHERE u.Email = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Role role = new Role(rs.getInt("RoleId"),
                            rs.getString("RoleName"),
                            rs.getString("Description"));
                    user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setRole(role);
                    user.setUsername(rs.getString("Username"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setFullName(rs.getString("FullName"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setAvatar(rs.getString("Avatar"));
                    user.setScore(rs.getInt("Score"));
                    user.setSex(rs.getString("Sex"));
                    user.setBirthDate(rs.getDate("BirthDate"));
                    user.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    user.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                    user.setLocked(rs.getBoolean("Locked"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<Review> getReviewsByProductId(int productId) {
        List<Review> reviews = new ArrayList<>();
        try {
            String sql = "SELECT u.username, r.rating, r.comment, r.created_at "
                    + "FROM Reviews r "
                    + "JOIN Users u ON r.user_id = u.UserID "
                    + "WHERE r.product_id = ? "
                    + "ORDER BY r.created_at DESC";
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(new Review(
                        rs.getString("username"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // Thêm đánh giá mới
    public boolean addReview(int userId, int productId, int rating, String comment) {
        String sql = "INSERT INTO Reviews (user_id, product_id, rating, comment) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== Các phương thức mới hỗ trợ reset mật khẩu =====
    // Cập nhật token reset cho người dùng dựa trên email
    public void updateResetToken(String email, String token) {
        String sql = "UPDATE Users SET resetToken = ?, resetTokenExpiry = ? WHERE Email = ?";
        // Tính thời gian hết hạn là 5 phút sau thời điểm hiện tại
        Timestamp expiry = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.setTimestamp(2, expiry);
            stmt.setString(3, email);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Tìm người dùng theo token reset
    public User findUserByResetToken(String token) {
        User user = null;
        // Điều kiện resetTokenExpiry > CURRENT_TIMESTAMP đảm bảo token chưa hết hạn.
        String sql = "SELECT u.UserID, u.Username, u.Email, u.Password, u.FullName, "
                + "       u.PhoneNumber, u.Avatar, u.Score, u.Sex, "
                + "       u.BirthDate, u.CreatedAt, u.UpdatedAt, u.Locked, u.resetToken, u.resetTokenExpiry, "
                + "       r.RoleID AS RoleId, r.RoleName, r.Description "
                + "FROM Users u "
                + "INNER JOIN Roles r ON u.RoleId = r.RoleID "
                + "WHERE u.resetToken = ? AND u.resetTokenExpiry > CURRENT_TIMESTAMP";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Role role = new Role(rs.getInt("RoleId"),
                            rs.getString("RoleName"),
                            rs.getString("Description"));
                    user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setRole(role);
                    user.setUsername(rs.getString("Username"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setFullName(rs.getString("FullName"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setAvatar(rs.getString("Avatar"));
                    user.setScore(rs.getInt("Score"));
                    user.setSex(rs.getString("Sex"));
                    user.setBirthDate(rs.getDate("BirthDate"));
                    user.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    user.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                    user.setLocked(rs.getBoolean("Locked"));
                    user.setResetToken(rs.getString("resetToken"));
                    user.setResetTokenExpiry(rs.getTimestamp("resetTokenExpiry"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    // Cập nhật mật khẩu mới dựa trên token và xoá token sau khi cập nhật thành công
    public boolean updatePasswordByToken(String token, String newPassword) {
        String sql = "UPDATE Users SET Password = ?, resetToken = NULL, resetTokenExpiry = NULL WHERE resetToken = ? AND resetTokenExpiry > CURRENT_TIMESTAMP";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, token);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void setResetTokenExpiry(String email, Timestamp expiry) {
        String sql = "UPDATE Users SET resetTokenExpiry = ? WHERE Email = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, expiry);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Kết nối database

        // Khởi tạo DAO
        UserDAO reviewDAO = new UserDAO();

        // Test thêm đánh giá
        boolean added = reviewDAO.addReview(1, 2, 5, "Sản phẩm rất tốt, chất lượng tuyệt vời!");
        if (added) {
            System.out.println("Thêm đánh giá thành công!");
        } else {
            System.out.println("Thêm đánh giá thất bại!");
        }

        // Test lấy danh sách đánh giá theo productId
        int testProductId = 2;
        List<Review> reviews = reviewDAO.getReviewsByProductId(testProductId);

        System.out.println("Danh sách đánh giá cho sản phẩm ID " + testProductId + ":");
        if (reviews.isEmpty()) {
            System.out.println("Không có đánh giá nào.");
        } else {
            for (Review review : reviews) {
                System.out.println("- " + review);
            }
        }
    }

//    public User checkLogin(String username, String password) {
//        String sql = "SELECT * FROM Users WHERE username=? AND password=?";
//        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
//
//            st.setString(1, username);
//            st.setString(2, password);
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()) {
//                // Lấy thông tin Role từ RoleId
//                Role role = getRoleById(rs.getInt("RoleId"));
//
//                User user = new User();
//                user.setUserId(rs.getInt("UserID"));
//                user.setRole(role);
//                user.setUsername(rs.getString("Username"));
//                user.setPassword(rs.getString("Password"));
//                user.setEmail(rs.getString("Email"));
//                user.setFullName(rs.getString("FullName"));
//                user.setPhoneNumber(rs.getString("PhoneNumber"));
//                user.setAvatar(rs.getString("Avatar"));
//                user.setSex(rs.getString("Sex"));
//                user.setLocked(rs.getBoolean("Locked"));
//                user.setBirthDate(rs.getDate("BirthDate")); // Lưu ý: kiểu dữ liệu java.sql.Date
//                user.setCreatedAt(rs.getTimestamp("CreatedAt"));
//                user.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
//                // score giữ giá trị mặc định là 0 (hoặc bạn có thể set lại nếu cần)
//                user.setResetToken(null);        // resetToken không có trong bảng Users
//                user.setResetTokenExpiry(null);  // resetTokenExpiry không có trong bảng Users
//
//                return user;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public Role getRoleById(int roleId) {
//        String sql = "SELECT * FROM Roles WHERE RoleId = ?";
//        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
//
//            st.setInt(1, roleId);
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()) {
//                Role role = new Role();
//                role.setRoleId(rs.getInt("RoleId"));
//                role.setRoleName(rs.getString("RoleName"));
//                role.setDescription(rs.getString("Description"));
//                return role;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
