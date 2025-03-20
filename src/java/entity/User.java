package entity;

import java.sql.Timestamp;
import java.util.Date;

public class User {

    private int userId;

    private Role role;

    private String username;

    private String password;

    private String email;

    private String fullName;

    private String phoneNumber;

    private String avatar;

    private int score = 0;

    private String sex;

    private boolean locked = false;

    private Date birthDate;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private String resetToken;
    
    private Timestamp resetTokenExpiry;

    public User() {
    }

    
    
    public User(int userId, Role role, String username, String password, String email, String fullName,
            String phoneNumber, String avatar, String sex, Date birthDate, Timestamp createdAt, Timestamp updatedAt) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.sex = sex;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(Role role, String username, String password, String email, String fullName,
            String phoneNumber, String avatar, int score, String sex, Date birthDate) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.score = score;
        this.sex = sex;
        this.birthDate = birthDate;
    }

    public User(int userId, Role role, String username, String password, String email, String fullName, String phoneNumber, String avatar, String sex, Date birthDate, Timestamp createdAt, Timestamp updatedAt, String resetToken, Timestamp resetTokenExpiry) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.sex = sex;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.resetToken = resetToken;
        this.resetTokenExpiry = resetTokenExpiry;
    }

    public User(Role role, String username, String password, String email, String fullName, String phoneNumber, String avatar, String sex, Date birthDate, Timestamp createdAt, Timestamp updatedAt, String resetToken, Timestamp resetTokenExpiry) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.sex = sex;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.resetToken = resetToken;
        this.resetTokenExpiry = resetTokenExpiry;
    } 
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Timestamp getResetTokenExpiry() {
        return resetTokenExpiry;
    }

    public void setResetTokenExpiry(Timestamp resetTokenExpiry) {
        this.resetTokenExpiry = resetTokenExpiry;
    }    

    @Override
    public String toString() {
        return "user{" + "userId=" + userId + ", role=" + role + ", username=" + username + ", password=" + password + ", email=" + email + ", "
                + "fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", avatar=" + avatar + ", score=" + score + ", sex=" + sex + ", "
                + "locked=" + locked + ", birthDate=" + birthDate + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

}
