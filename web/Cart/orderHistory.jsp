<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="entity.Order"%>
<%@page import="entity.OrderDetail"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Order History</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    </head>
    <body>
         <jsp:include page="/Common/header.jsp"></jsp:include>
        <div class="container mt-5">
            <h2>Order History</h2>

            <c:if test="${empty orders}">
                <p>You have not placed any orders yet.</p>
            </c:if>

            <c:if test="${not empty orders}">
                <div class="accordion" id="orderAccordion">
                    <c:forEach var="order" items="${orders}">
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="heading${order.orderId}">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${order.orderId}" aria-expanded="false" aria-controls="collapse${order.orderId}">
                                    Order ID: ${order.orderId} - Date: ${order.orderDate} - Total: 
                                    <!-- Định dạng số mà không có phần thập phân thừa -->
                                    <fmt:formatNumber value="${order.totalAmount}" type="number" pattern="#,###" /> VND
                                </button>
                            </h2>
                            <div id="collapse${order.orderId}" class="accordion-collapse collapse" aria-labelledby="heading${order.orderId}" data-bs-parent="#orderAccordion">
                                <div class="accordion-body">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Product ID</th>
                                                <th>Product Image</th>
                                                <th>Product Name</th>
                                                <th>Quantity</th>
                                                <th>Price</th>
                                                <th>Total Price</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- Duyệt qua chi tiết đơn hàng -->
                                            <c:forEach var="detail" items="${order.orderDetails}">
                                                <tr>
                                                    <td>${detail.productId}</td>
                                                    <td><img src="${detail.productImage}" width="50"></td>
                                                    <td>${detail.productName}</td>
                                                    <td>${detail.quantity}</td>
                                                    <td><fmt:formatNumber value="${detail.price}" type="number" pattern="#,###" /> VND</td>
                                                    <td><fmt:formatNumber value="${detail.totalPrice}" type="number" pattern="#,###" /> VND</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
                 
        </div>
        <jsp:include page="/Common/footer.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>