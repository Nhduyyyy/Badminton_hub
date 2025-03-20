package controller;

import ChatMessageDAO.ChatMessageDAO;
import entity.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/FetchMessagesServlet")
public class FetchMessagesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Message> messages = ChatMessageDAO.getMessages();
        
        JSONArray jsonArr = new JSONArray();
        for (Message msg : messages) {
            JSONObject obj = new JSONObject();
            obj.put("sender", msg.getSender());
            obj.put("text", msg.getText());
            obj.put("timestamp", msg.getTimestamp());
            jsonArr.put(obj);
        }
        
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonArr.toString());
        out.flush();
    }
}
