
package controller;

import entity.User;
import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.loginService;
import uitl.EmailService;

@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {

    private loginService loginService = new loginService();
    private EmailService emailService = new EmailService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/authenticator_authorization/forgotPassword.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        
        // Kiểm tra xem email có tồn tại hay không
        User user = loginService.findUserByEmail(email);
        if (user == null) {
            request.setAttribute("errorMessage", "Email không tồn tại.");
            request.getRequestDispatcher("/authenticator_authorization/forgotPassword.jsp").forward(request, response);
            return;
        }
        
        // Tạo mã token reset sử dụng UUID
        String token = UUID.randomUUID().toString();
        
        // Cập nhật token vào cơ sở dữ liệu (chỉ dành cho tác vụ reset mật khẩu)
        loginService.updateResetToken(email, token);
        
        // Tạo đường link reset mật khẩu
        String resetLink = request.getScheme() + "://" +
                           request.getServerName() + ":" +
                           request.getServerPort() +
                           request.getContextPath() + "/resetPassword?token=" + token;
        
        // Nội dung email gửi đi
        String subject = "Reset mật khẩu của bạn";
        String messageContent = "<p>Xin chào,</p>"
                + "<p>Bạn đã yêu cầu đặt lại mật khẩu. Vui lòng click vào đường link dưới đây để thay đổi mật khẩu:</p>"
                + "<p><a href=\"" + resetLink + "\">Reset mật khẩu</a></p>"
                + "<br>"
                + "<p>Nếu bạn không yêu cầu, vui lòng bỏ qua email này.</p>";
        
        // Gửi email sử dụng EmailService
        boolean emailSent = emailService.send(email, subject, messageContent);
        
        if(emailSent){
            request.setAttribute("message", "Vui lòng kiểm tra email của bạn để đặt lại mật khẩu.");
        } else {
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi gửi email.");
        }
        request.getRequestDispatcher("/authenticator_authorization/forgotPassword.jsp").forward(request, response);
    }
}
