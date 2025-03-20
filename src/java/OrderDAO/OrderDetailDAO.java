package OrderDAO;

import context.DBContext;
import java.sql.*;
import entity.OrderDetail;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {

    private Connection conn;


    public OrderDetailDAO() {
        this.conn = DBContext.getConnection();
    }

    public void addOrderDetail(OrderDetail orderDetail) throws SQLException {
        String sql = "INSERT INTO OrderDetails (order_id, product_id,product_image,product_name, quantity, price, total_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getOrderId());
            stmt.setInt(2, orderDetail.getProductId());
            stmt.setString(3, orderDetail.getProductImage());
            stmt.setString(4, orderDetail.getProductName());
            stmt.setInt(5, orderDetail.getQuantity());
            stmt.setDouble(6, orderDetail.getPrice());
            stmt.setDouble(7, orderDetail.getQuantity() * orderDetail.getPrice());
            stmt.executeUpdate();
        }
    }


    public boolean saveOrderDetails(int orderId, List<OrderDetail> orderDetails) {
        String query = "INSERT INTO OrderDetails (order_id, product_id, product_image, product_name, quantity, price, total_price) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            for (OrderDetail detail : orderDetails) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, detail.getProductId());
                stmt.setString(3, detail.getProductImage());
                stmt.setString(4, detail.getProductName());
                stmt.setInt(5, detail.getQuantity());
                stmt.setDouble(6, detail.getPrice());
                stmt.setDouble(7, detail.getTotalPrice());
                stmt.addBatch();
            }

            int[] rows = stmt.executeBatch();
            System.out.println("✅ Đã lưu " + rows.length + " sản phẩm vào OrderDetails");
            return rows.length > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails WHERE order_id = ?";  // Truy vấn chi tiết đơn hàng theo order_id

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderDetailId(rs.getInt("orderDetail_id"));
                orderDetail.setOrderId(rs.getInt("order_id"));
                orderDetail.setProductId(rs.getInt("product_id"));
                orderDetail.setProductImage(rs.getString("product_image"));
                orderDetail.setProductName(rs.getString("product_name"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setPrice(rs.getDouble("price"));
                orderDetail.setTotalPrice(rs.getDouble("total_price"));

                orderDetails.add(orderDetail);
            }
        }
        return orderDetails;
    }

}