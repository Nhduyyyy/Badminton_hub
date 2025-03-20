/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserDAO;

import entity.User;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public interface IUserDAO {
        User authenticateUser(String identifier, String password);
    
    
    void insertUser(User user) throws SQLException;
    
    
    List<User> selectAllUsers();
    
    User findById(int id) throws SQLException;
    
    
    boolean deleteUser(int id) throws SQLException;
    
    
    boolean updateUser(User user) throws SQLException;
    
    List<User> searchUsers(String query);
        
    public List<User> filterUsersPaginated(String roleFilter, String statusFilter, int offset, int pageSize, Integer excludeUserId);
    
    public int countUsers(String roleFilter, String statusFilter, Integer excludeUserId);
    public User findByEmail(String email);
}
