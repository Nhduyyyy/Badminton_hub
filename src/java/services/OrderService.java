package services;

import OrderDAO.OrderDAO;
import OrderDAO.OrderDetailDAO;
import ProductDAO.ProductDAO;
import context.DBContext;
import java.sql.Connection;
import entity.Order;
import entity.OrderDetail;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class OrderService {

    Connection conn = DBContext.getConnection();
    private final OrderDAO orderDAO;
    private final OrderDetailDAO orderDetailDAO;
    private final ProductDAO productDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO(conn);
        this.orderDetailDAO = new OrderDetailDAO();
        this.productDAO = new ProductDAO();
    }

    public boolean placeOrder(int userId, List<OrderDetail> cartItems) {
        try {
            conn.setAutoCommit(false);
            double totalPrice = 0;
            for (OrderDetail item : cartItems) {
                totalPrice += item.getQuantity() * item.getPrice();
            }

            int orderId = orderDAO.createOrder(userId, totalPrice, "PENDING", LocalDateTime.now());
            if (orderId == -1) {
                conn.rollback();
                return false;
            }

            for (OrderDetail item : cartItems) {
                int stock = productDAO.getStockByProductId(item.getProductId());
                if (stock < item.getQuantity()) {
                    conn.rollback();
                    return false;
                }
                productDAO.updateProductStock(item.getProductId(), stock - item.getQuantity());

                item.setOrderId(orderId);
                orderDetailDAO.addOrderDetail(item);
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        return orderDetailDAO.getOrderDetailsByOrderId(orderId);
    }

    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }

    public boolean updateOrderStatus(int orderId, String newStatus) throws SQLException {
        return orderDAO.updateOrderStatus(orderId, newStatus);
    }

    public int generateOrderId() {
        return orderDAO.generateOrderId();
    }

    public void updateProductQuantity(String orderId) throws SQLException {
        List<OrderDetail> orderItems = orderDAO.getOrderItemsByOrderId(orderId);

        for (OrderDetail item : orderItems) {
            int productId = item.getProductId();
int newStock = productDAO.getCurrentStock(productId) - item.getQuantity();

            if (newStock >= 0) {
                productDAO.updateProductStock(productId, newStock);
            } else {
                throw new SQLException("Không đủ hàng trong kho cho sản phẩm: " + productId);
            }
        }
    }

    public boolean processOrder(int userId, long amount, List<OrderDetail> orderDetails) {
        int orderId = orderDAO.saveOrder(userId, amount); // Lưu vào Orders
        if (orderId == -1) {
            System.out.println("❌ Lỗi khi tạo đơn hàng");
            return false;
        }

        boolean detailsSaved = orderDetailDAO.saveOrderDetails(orderId, orderDetails); // Lưu vào OrderDetails
        if (!detailsSaved) {
            System.out.println("❌ Lỗi khi lưu chi tiết đơn hàng");
            return false;
        }

        boolean stockUpdated = orderDAO.updateStockAfterOrder(orderId); // Cập nhật tồn kho
        if (!stockUpdated) {
            System.out.println("❌ Lỗi khi cập nhật số lượng sản phẩm");
            return false;
        }

        return true; // Thành công
    }

    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        List<Order> orders = orderDAO.getOrdersByUserId(userId);

        for (Order order : orders) {
            List<OrderDetail> orderDetails = orderDetailDAO.getOrderDetailsByOrderId(order.getOrderId());
            order.setOrderDetails(orderDetails);
        }

        return orders;
    }

}