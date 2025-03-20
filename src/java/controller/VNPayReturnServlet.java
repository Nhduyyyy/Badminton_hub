package controller;

import entity.Cart;
import entity.CartItem;
import entity.OrderDetail;
import entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import services.OrderService;
import uitl.EmailService;
import uitl.IJavaMail;

@WebServlet(name = "VNPayReturnServlet", urlPatterns = {"/vnpayreturn"})
public class VNPayReturnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy dữ liệu đơn hàng từ session
        List<OrderDetail> orderDetails = (List<OrderDetail>) session.getAttribute("orderDetails");
        Long amount = (Long) session.getAttribute("amount");
        Integer userId = (Integer) session.getAttribute("userId");
        User user = (User) session.getAttribute("user");

        // Lấy mã phản hồi từ VNPay
        Cart cart = (Cart) session.getAttribute("cart");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        System.out.println("VNPay Response Code: " + vnp_ResponseCode);

        if ((orderDetails == null || orderDetails.isEmpty()) && cart != null) {
            orderDetails = new ArrayList<>();
            for (CartItem item : cart.getItems().values()) {
                OrderDetail detail = new OrderDetail(
                        0, 0,
                        item.getProduct().getProductId(),
                        item.getProduct().getImage(),
                        item.getProduct().getProductName(),
                        item.getQuantity(),
                        item.getProduct().getPrice(),
                        item.getProduct().getPrice() * item.getQuantity()
                );
                orderDetails.add(detail);
            }
        }

        // Kiểm tra dữ liệu có đầy đủ không
        if (orderDetails == null || orderDetails.isEmpty() || userId == null || amount == null) {
            System.out.println("❌ Thiếu thông tin đơn hàng");
            response.sendRedirect("/badminton/Cart/cart.jsp");
            return;
        }

        // Nếu thanh toán thành công (vnp_ResponseCode = "00")
        if ("00".equals(vnp_ResponseCode)) {
            OrderService orderService = new OrderService();
            boolean success = orderService.processOrder(userId, amount, orderDetails);

            if (success) {
                // ✅ Nếu lưu đơn hàng thành công, xóa giỏ hàng
                session.removeAttribute("cart");
                session.removeAttribute("orderDetails");
                session.removeAttribute("amount");

                session.removeAttribute("userId");

                // Gửi email xác nhận cho người dùng
                if (user != null) {
                    String subject = "Chúc mừng đăng ký thành công";
                    String message = "<html>\n"
                            + "  <head>\n"
                            + "    <meta charset=\"UTF-8\">\n"
                            + "    <style>\n"
                            + "      body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }\n"
                            + "      .container { max-width: 600px; margin: 30px auto; background-color: #ffffff; padding: 20px; border: 1px solid #dddddd; }\n"
                            + "      .header { background-color: #2c3e50; color: #ffffff; padding: 10px; text-align: center; }\n"
                            + "      .content { margin: 20px 0; font-size: 18px; text-align: center; }\n"
                            + "      .footer { font-size: 12px; color: #777777; text-align: center; margin-top: 20px; }\n"
                            + "    </style>\n"
                            + "  </head>\n"
                            + "  <body>\n"
                            + "    <div class='container'>\n"
                            + "      <div class='header'>\n"
                            + "        <h2>Thông Báo</h2>\n"
                            + "      </div>\n"
                            + "      <div class='content'>\n"
                            + "        <p>Chúc mừng bạn đã mua hàng thành công!</p>\n"
                            + "      </div>\n"
                            + "      <div class='footer'>\n"
                            + "        <p>Trân trọng,</p>\n"
                            + "        <p>Duy Đẹp Trai VL</p>\n"
                            + "      </div>\n"
                            + "    </div>\n"
                            + "  </body>\n"
                            + "</html>";

                    IJavaMail emailService = new EmailService();
                    emailService.send(user.getEmail(), subject, message);
                }

                System.out.println("✅ Đơn hàng xử lý thành công!");
                response.sendRedirect("/badminton/Cart/orderSuccess.jsp");
            } else {
                System.out.println("❌ Xử lý đơn hàng thất bại!");
                response.sendRedirect("badminton/login");
            }
        } else {
            System.out.println("❌ Thanh toán thất bại!");
            response.sendRedirect("checkout.jsp?error=payment_failed");
        }
    }
}
