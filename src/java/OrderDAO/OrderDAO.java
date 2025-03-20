package OrderDAO;

import context.DBContext;
import entity.Order;
import entity.OrderDetail;
import entity.Role;
import entity.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    private Connection conn;

    public OrderDAO() {
    }

    public OrderDAO(Connection conn) {
        this.conn = DBContext.getConnection();
    }

    public int createOrder(int userId, double totalPrice, String status, LocalDateTime orderDate) throws SQLException {
        String sql = "INSERT INTO Orders (user_id, total_price, status, order_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.setDouble(2, totalPrice);
            pstmt.setString(3, status);
            pstmt.setTimestamp(4, Timestamp.valueOf(orderDate));
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE user_id = ? ORDER BY order_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("total_price"),
                        rs.getString("status"),
                        rs.getTimestamp("order_date").toLocalDateTime()
                ));
            }
        }
        return orders;
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders ORDER BY order_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("total_price"),
                        rs.getString("status"),
                        rs.getTimestamp("order_date").toLocalDateTime()
                ));
            }
        }
        return orders;
    }

    public boolean updateOrderStatus(int orderId, String newStatus) throws SQLException {
        String sql = "UPDATE Orders SET status = ? WHERE order_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, orderId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public int generateOrderId() {
        int orderId = -1;
        String sql = "INSERT INTO Orders (user_id, total_price) VALUES (?, ?)";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, 0);
            stmt.setDouble(2, 0.0);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    public List<OrderDetail> getOrderItemsByOrderId(String orderId) throws SQLException {
        List<OrderDetail> orderItems = new ArrayList<>();
        String query = "SELECT product_id, quantity FROM OrderDetails WHERE order_id = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                orderItems.add(new OrderDetail(productId, quantity));
            }
        }
        return orderItems;
    }

    public int saveOrder(int userId, double amount) {
        String query = "INSERT INTO Orders (user_id, total_price, status, order_date) VALUES (?, ?, 'Pending', GETDATE())";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, userId);
            System.out.println("userId");
            stmt.setDouble(2, amount);
            System.out.println("amount");
            int rows = stmt.executeUpdate();
            System.out.println("rows");

            if (rows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    System.out.println("✅ Đã lưu đơn hàng vào Orders: orderId = " + orderId);
                    return orderId;
                }
            }
            System.out.println("❌ Không thể lưu đơn hàng vào Orders");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu lỗi
    }

    public boolean updateStockAfterOrder(int orderId) {
        // Kiểm tra tồn kho trước khi cập nhật
        String checkStockQuery = "SELECT p.product_id, p.stock, od.quantity "
                + "FROM Products p INNER JOIN OrderDetails od ON p.product_id = od.product_id "
                + "WHERE od.order_id = ? AND p.stock < od.quantity";

        try (Connection conn = DBContext.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkStockQuery)) {

            checkStmt.setInt(1, orderId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("❌ Không đủ hàng trong kho cho sản phẩm ID: " + rs.getInt("product_id"));
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi SQL khi kiểm tra tồn kho: " + e.getMessage());
            return false;
        }

        // Cập nhật tồn kho nếu đủ hàng
        String updateQuery = "UPDATE Products "
                + "SET stock = stock - (SELECT SUM(quantity) FROM OrderDetails WHERE order_id = ?) "
                + "WHERE product_id IN (SELECT product_id FROM OrderDetails WHERE order_id = ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, orderId);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "✅ Đã cập nhật số lượng sản phẩm" : "❌ Không thể cập nhật tồn kho");
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi SQL khi cập nhật tồn kho: " + e.getMessage());
        }
        return false;
    }

    public List<Order> getOrderHistoryByUserId(int userId) {
        List<Order> orderHistory = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBContext.getConnection();

            String sql = "SELECT o.order_id, o.total_price, o.status, o.order_date, "
                    + "od.product_id, od.product_image, od.product_name, od.quantity, od.price, od.total_price AS item_total_price "
                    + "FROM Orders o "
                    + "JOIN OrderDetails od ON o.order_id = od.order_id "
                    + "WHERE o.user_id = ? "
                    + "ORDER BY o.order_date DESC";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            Map<Integer, Order> orderMap = new LinkedHashMap<>();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");

                Order order = orderMap.get(orderId);

                if (order == null) {
                    order = new Order();
                    order.setOrderId(orderId);
                    order.setTotalAmount(rs.getDouble("total_price"));
                    order.setStatus(rs.getString("status"));
                    order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                    order.setOrderDetails(new ArrayList<>());

                    orderMap.put(orderId, order);
                }

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setProductId(rs.getInt("product_id"));
                orderDetail.setProductImage(rs.getString("product_image"));
                orderDetail.setProductName(rs.getString("product_name"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setPrice(rs.getDouble("price"));
                orderDetail.setTotalPrice(rs.getDouble("total_price"));

                order.getOrderDetails().add(orderDetail);
            }

            orderHistory.addAll(orderMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderHistory;
    }

    

}
