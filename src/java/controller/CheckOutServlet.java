package controller;

import entity.Cart;
import entity.OrderDetail;
import entity.User;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@WebServlet(name = "CheckOutServlet", urlPatterns = {"/checkout"})
public class CheckOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("checkout.jsp?error=cart_empty");
            return;
        }

        List<OrderDetail> orderDetails = new ArrayList<>();
        long[] totalAmount = {0};

        cart.getItems().forEach((productId, cartItem) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductId(productId);
            orderDetail.setProductName(cartItem.getProduct().getProductName());
            orderDetail.setProductImage(cartItem.getProduct().getImage());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
            orderDetail.setTotalPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice());
            orderDetails.add(orderDetail);

            totalAmount[0] += orderDetail.getTotalPrice(); // Cộng dồn tổng tiền
        });

        session.setAttribute("orderDetails", orderDetails);

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("checkout.jsp?error=invalid_request");
            return;
        }

        int userId = user.getUserId();

        long amount = totalAmount[0] * 100; // Chuyển đổi thành đơn vị VNPay yêu cầu (VND x 100)

        session.setAttribute("userId", userId);
        session.setAttribute("amount", amount);

        System.out.println("✅ userId lưu vào session: " + userId);
        System.out.println("✅ amount lưu vào session: " + amount);

        String orderId = String.valueOf(System.currentTimeMillis());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(Calendar.getInstance().getTime());

        String vnp_IpAddr = request.getRemoteAddr();
if (vnp_IpAddr == null || vnp_IpAddr.isEmpty()) {
            vnp_IpAddr = "127.0.0.1";
        }

        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        params.put("vnp_Amount", String.valueOf(amount));
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", orderId);
        params.put("vnp_OrderInfo", "Thanh toán đơn hàng: " + orderId);
        params.put("vnp_OrderType", "billpayment");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", "http://localhost:8080/badminton/vnpayreturn");
        params.put("vnp_CreateDate", vnp_CreateDate);
        params.put("vnp_IpAddr", vnp_IpAddr);

        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=')
                        .append(URLEncoder.encode(fieldValue, "UTF-8")).append('&');
                query.append(fieldName).append('=')
                        .append(URLEncoder.encode(fieldValue, "UTF-8")).append('&');
            }
        }

        hashData.setLength(hashData.length() - 1);
        query.setLength(query.length() - 1);

        String vnp_SecureHash = hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + query.toString();
        response.sendRedirect(paymentUrl);
    }



    private String hmacSHA512(String key, String data) {
        try {
            Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            sha512_HMAC.init(secretKey);
            byte[] hash = sha512_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo HMAC SHA-512", e);
        }
    }
}
