package controller;

import ChatMessageDAO.ChatMessageDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Message;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Bot token của Telegram
    private final String TELEGRAM_BOT_TOKEN = "7417200469:AAGyfohBZWtCDhyzx54pQ9hmjERgrC5SDcw";
    // Chat ID của người bán trên Telegram (seller)
    private final String SELLER_TELEGRAM_CHAT_ID = "5021568780";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tin nhắn của người mua từ giao diện website
        String buyerMessage = request.getParameter("message");
        if (buyerMessage == null || buyerMessage.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tin nhắn không được để trống");
            return;
        }

        // Lưu tin nhắn của người mua vào CSDL với sender là "Buyer"
        ChatMessageDAO.addMessage(new Message("Buyer", buyerMessage, System.currentTimeMillis()));

        // Luôn gửi thông báo tin nhắn của người mua đến Telegram để thông báo người bán
        String notification = "Bạn có tin nhắn mới từ khách hàng:\n" + buyerMessage;
        sendMessageToTelegram(SELLER_TELEGRAM_CHAT_ID, notification);

        // Lấy session hiện tại (tạo mới nếu chưa có)
        HttpSession session = request.getSession(true);
        Boolean firstMessageSent = (Boolean) session.getAttribute("firstMessageSent");

        // Nếu đây là tin nhắn đầu tiên trong phiên, gửi thêm tin auto-reply
        if (firstMessageSent == null || !firstMessageSent) {
            String autoReply = "Chào bạn, cảm ơn đã gửi tin. Chúng tôi sẽ liên hệ lại sớm.";

            // Lưu tin nhắn auto-reply vào DB với sender "Seller AutoReply"
            ChatMessageDAO.addMessage(new Message("Seller AutoReply", autoReply, System.currentTimeMillis()));

            // Gửi tin auto-reply đến Telegram (tùy chọn, nếu muốn người bán cũng nhận được tin này)
            sendMessageToTelegram(SELLER_TELEGRAM_CHAT_ID, "Auto-reply: " + autoReply);

            // Đánh dấu trong session rằng auto-reply đã được gửi
            session.setAttribute("firstMessageSent", true);
        }

        // Trả về phản hồi cho giao diện website
        response.setContentType("text/plain; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.write("Tin nhắn của bạn đã được gửi. Người bán sẽ nhận được thông báo qua Telegram.");
        }
    }

    // Phương thức gửi tin nhắn đến Telegram qua API sendMessage
    private void sendMessageToTelegram(String chatId, String message) {
        HttpURLConnection conn = null;
        try {
            String apiUrl = "https://api.telegram.org/bot" + TELEGRAM_BOT_TOKEN + "/sendMessage";
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // Sử dụng JSONObject để xây dựng payload
            JSONObject payload = new JSONObject();
            payload.put("chat_id", chatId);
            payload.put("text", message);
            String jsonPayload = payload.toString();

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.getBytes("UTF-8"));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Lỗi gửi tin nhắn, HTTP response code: " + responseCode);
            }

            StringBuilder resp = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = in.readLine()) != null) {
                    resp.append(line);
                }
            }
            System.out.println("Thông báo gửi đến người bán: " + resp.toString());
        } catch (Exception e) {
            System.err.println("Exception khi gửi tin nhắn đến Telegram: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
