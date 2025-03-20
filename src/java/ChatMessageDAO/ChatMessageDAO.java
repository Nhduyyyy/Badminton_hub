package ChatMessageDAO;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import entity.Message;

public class ChatMessageDAO {

    // Lưu tin nhắn vào DB
    public static void addMessage(Message msg) {
        String sql = "INSERT INTO ChatMessages (sender, text, timestamp) VALUES (?, ?, ?)";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, msg.getSender());
            ps.setString(2, msg.getText());
            ps.setLong(3, msg.getTimestamp());
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Lấy danh sách tin nhắn từ DB
    public static List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT sender, text, timestamp FROM ChatMessages ORDER BY id ASC";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String sender = rs.getString("sender");
                String text = rs.getString("text");
                long timestamp = rs.getLong("timestamp");
                messages.add(new Message(sender, text, timestamp));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return messages;
    }
}
