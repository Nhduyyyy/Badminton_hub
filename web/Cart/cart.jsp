<%@page import="java.util.ArrayList"%>
<%@page import="entity.OrderDetail"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="entity.CartItem"%>
<%@page import="entity.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 0;
            }
            .wrapper {
                height: 100vh;
            }
            .container {
                width: 90%;
                max-width: 1100px;
                margin: 30px auto;
                background: #fff;
                padding: 20px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
            }
            h2 {
                text-align: center;
                color: #333;
                font-size: 22px;
                margin-bottom: 20px;
                font-weight: 600;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background: #fff;
            }
            th, td {
                padding: 14px;
                text-align: center;
                border-bottom: 1px solid #ddd;
            }
            th {
                background: #007bff;
                color: white;
                font-weight: bold;
                text-transform: uppercase;
            }
            tr:hover {
                background: rgba(0, 123, 255, 0.1);
            }
            .product-img {
                width: 75px;
                height: 75px;
                object-fit: cover;
                border-radius: 6px;
            }
            .remove-btn {
                background: none;
                border: none;
                cursor: pointer;
                font-size: 18px;
                color: #ff4757;
                transition: color 0.3s ease;
            }
            .remove-btn:hover {
                color: #e84118;
            }
            .out-of-stock {
                color: red;
                font-size: 14px;
                font-weight: bold;
            }
            .quantity-container {
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .quantity-btn {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 5px 10px;
                cursor: pointer;
                font-size: 16px;
                margin: 0 5px;
                border-radius: 4px;
            }
            .quantity-btn:hover {
                background-color: #0056b3;
            }
            .quantity-input {
                width: 50px;
                text-align: center;
                font-size: 16px;
                padding: 5px;
            }
        </style>
    </head> 
    <body>
        <div class="wrapper">
            <div class="header">
                <jsp:include page="/Common/header.jsp" />
            </div>

            <div class="container">
                <h2>Shopping Cart</h2>

                <c:if test="${not empty sessionScope.cart.items}">
                    <table border="1px" width="100%">
                        <c:set var="totalPrice" value="0" />
                        <c:forEach var="item" items="${sessionScope.cart.items}">
                            <c:set var="itemTotal" value="${item.value.quantity * item.value.product.price}" />
                            <c:set var="totalPrice" value="${totalPrice + itemTotal}" />
                            <tr>
                                <td><img src="${item.value.product.image}" width="50"></td>
                                <td>${item.value.product.productName}</td>
                                <td>
                                    <form action="/badminton/cart" method="post" id="updateForm_${item.key}">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="productId" value="${item.key}">

                                        <div class="quantity-container">
                                            <button type="button" class="quantity-btn" onclick="changeQuantity(${item.key}, -1, ${item.value.product.stock})">-</button>
                                            <input type="number" id="quantity_${item.key}" name="quantity" value="${item.value.quantity}" min="1" max="${item.value.product.stock}" class="quantity-input" oninput="validateQuantity(${item.key}, ${item.value.product.stock})">
                                            <button type="button" class="quantity-btn" onclick="changeQuantity(${item.key}, 1, ${item.value.product.stock})">+</button>
                                        </div>

                                        <p id="stockWarning_${item.key}" class="out-of-stock" style="display: none;">Hết hàng</p>

                                        <button type="submit" id="updateBtn_${item.key}" disabled>Update</button>
                                    </form>
                                </td>
                                <td>${item.value.product.price}</td>
                                <td><fmt:formatNumber value="${itemTotal}" pattern="#,##0.##"/></td>

                                <td>
                                    <form action="/badminton/cart" method="post" onsubmit="return confirmDelete();">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="productId" value="${item.key}">
                                        <button type="submit" class="remove-btn"><i class="fas fa-trash-alt"></i></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                    <h3>Tổng tiền: <fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="" groupingUsed="true"/> VND</h3>

                    <form action="/badminton/checkout" method="post">
                        <input type="hidden" name="amount" value="${totalPrice}">
                        <button type="submit">Đặt hàng</button>
                    </form>
                </c:if>
            </div>

            <script>
                function confirmDelete() {
                    return confirm("Do you want to remove?");
                }

                function changeQuantity(productId, change, stock) {
                    let quantityInput = document.getElementById("quantity_" + productId);
                    let newQuantity = parseInt(quantityInput.value) + change;

                    if (newQuantity >= 1 && newQuantity <= stock) {
                        quantityInput.value = newQuantity;
                        validateQuantity(productId, stock);
                    }
                }

                function validateQuantity(productId, stock) {
                    let quantityInput = document.getElementById("quantity_" + productId);
                    let warning = document.getElementById("stockWarning_" + productId);
                    let updateBtn = document.getElementById("updateBtn_" + productId);
                    let quantity = parseInt(quantityInput.value);

                    if (quantity > stock) {
                        warning.style.display = "block";
                        updateBtn.disabled = true;
                    } else {
                        warning.style.display = "none";
                        updateBtn.disabled = false;
                    }
                }
            </script>
        </div>
    </body>
</html>

<jsp:include page="/Common/footer.jsp" />

