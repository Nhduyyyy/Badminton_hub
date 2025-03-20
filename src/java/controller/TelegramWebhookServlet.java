
package controller;

import ChatMessageDAO.ChatMessageDAO;
import java.io.BufferedReader;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Message;
import org.json.JSONObject;  // Thêm thư viện org.json vào project

@WebServlet("/TelegramWebhookServlet")
public class TelegramWebhookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Token và Chat_ID nếu cần cho việc gửi phản hồi (nếu auto-reply không dựa trên chat_id từ tin nhắn)
    private final String TELEGRAM_BOT_TOKEN = "7417200469:AAGyfohBZWtCDhyzx54pQ9hmjERgrC5SDcw";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Đọc dữ liệu JSON gửi từ Telegram
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
        }
        String jsonData = sb.toString();
        System.out.println("Received webhook data: " + jsonData);

        // Parse JSON
        JSONObject jsonObj = new JSONObject(jsonData);
        if (jsonObj.has("message")) {
            JSONObject messageObj = jsonObj.getJSONObject("message");
            String text = messageObj.optString("text", "");
            // Lấy thông tin người gửi
            JSONObject chatObj = messageObj.getJSONObject("chat");
            String sender = chatObj.optString("first_name", "Telegram");

            // Lưu tin nhắn vào DB
            ChatMessageDAO.addMessage(new Message("Telegram", text, System.currentTimeMillis()));
        }
        
        // Trả về 200 OK
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

