<%@page import="entity.Category"%>
<%@page import="java.util.List"%>
<%@page import="services.ProductService"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    ProductService dao = new ProductService();
    List<Category> listC = dao.getAllCategories();
    request.setAttribute("listC", listC);
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${product.productName}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <jsp:include page="/Common/header.jsp"></jsp:include>
            <!-- Custom CSS cho Phần Thông Số Kĩ Thuật và Đánh Giá -->
            <style>
                /* ========= Style cho Phần Thông Số Kĩ Thuật ========= */
                /* Giả sử đây là phần .row.mt-5 đầu tiên trong container */
                .row.mt-5:first-of-type > .col-12 {
                    background-color: #f8f9fa;
                    padding: 20px;
                    border: 1px solid #ddd;
                    border-radius: 5px;
                    margin-bottom: 30px;
                }
                .row.mt-5:first-of-type > .col-12 h4 {
                    border-bottom: 2px solid #dee2e6;
                    padding-bottom: 10px;
                    margin-bottom: 20px;
                    font-weight: bold;
                    color: #343a40;
                }

                /* ========= Style cho Phần Thêm Đánh Giá ========= */
                /* Giả sử đây là .row.mt-5 thứ 2 chứa form "Thêm đánh giá" */
                .row.mt-5:nth-of-type(2) > .col-md-8.mx-auto form {
                    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                    background-color: #ffffff;
                    border-radius: 5px;
                    padding: 20px;
                    margin-bottom: 30px;
                }
                .row.mt-5:nth-of-type(2) > .col-md-8.mx-auto h4 {
                    margin-bottom: 20px;
                    color: #495057;
                }

                /* ========= Style cho Phần Đánh Giá từ Người Dùng ========= */
                /* Giả sử đây là .row.mt-4 chứa danh sách đánh giá */
                .row.mt-4 > .col-md-8.mx-auto {
                    background-color: #ffffff;
                    border: 1px solid #e0e0e0;
                    border-radius: 5px;
                    padding: 20px;
                }
                .row.mt-4 > .col-md-8.mx-auto h4 {
                    margin-bottom: 20px;
                    border-bottom: 2px solid #e9ecef;
                    padding-bottom: 10px;
                    color: #495057;
                }
                /* Tùy biến style cho từng mục đánh giá */
                .row.mt-4 > .col-md-8.mx-auto .list-group-item {
                    border: none;
                    border-bottom: 1px solid #f1f1f1;
                    padding: 15px;
                    margin-bottom: 10px;
                }
                .row.mt-4 > .col-md-8.mx-auto .list-group-item:last-child {
                    border-bottom: none;
                }
            </style>
        </head>
        <body>
            <div class="container mt-5">
                <div class="row product" style="margin-left: 100px;">
                    <div class="col-md-4">
                        <img src="${product.image}" class="img-fluid" alt="${product.productName}">
                </div>
                <div class="col-md-7">
                    <h3>${product.productName}</h3>
                    <p>
                        <strong>Thương hiệu:</strong> ${brand.brandName} |
                        <strong>Tình trạng:</strong> <span class="text-success">${product.status}</span>
                        <strong> | Số lượng:</strong> ${product.stock}
                    </p>
                    <h4 class="text-danger">${product.price}</h4>

                    <div class="border p-3 mb-3">
                        <h5 class="text-warning">🎁 ƯU ĐÃI</h5>
                        <ul>
                            <li>Tặng 2 Quấn cán vợt Cầu Lông</li>
                            <li>Sản phẩm cam kết chính hãng</li>
                            <li>Thanh toán sau khi kiểm tra hàng</li>
                            <li>Bảo hành chính hãng theo nhà sản xuất</li>
                        </ul>
                        <h6 class="text-danger">🎁 Ưu đãi thêm khi mua sản phẩm tại VNB Premium</h6>
                        <ul>
                            <li>Sơn logo mặt vợt miễn phí</li>
                            <li>Bảo hành lưới đan trong 72 giờ</li>
                            <li>Thay gen vợt miễn phí trọn đời</li>
                            <li>Tích lũy điểm thành viên Premium</li>
                        </ul>
                    </div>

                    <form action="/badminton/cart" method="post" id="addToCartForm">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <input type="hidden" name="quantity" id="cartQuantity" value="1">

                        <div class="d-flex align-items-center mb-3">
                            <button type="button" class="btn btn-outline-secondary" onclick="changeQuantity(-1)">-</button>
                            <input type="text" id="quantity" class="form-control text-center mx-2" value="1" style="width: 50px;" readonly>
                            <button type="button" class="btn btn-outline-secondary" onclick="changeQuantity(1)">+</button>
                        </div>

                        <p id="stockWarning" class="text-danger mt-2" style="display: none;"></p>
                        <button type="submit" id="addToCartBtn" class="btn btn-danger">THÊM VÀO GIỎ HÀNG</button>
                    </form>

                    <script>
                        document.addEventListener("DOMContentLoaded", function () {
                            let stock = ${product.stock};
                            let quantityInput = document.getElementById("quantity");
                            let cartQuantityInput = document.getElementById("cartQuantity");
                            let stockWarning = document.getElementById("stockWarning");
                            let addToCartBtn = document.getElementById("addToCartBtn");

                            if (stock === 0) {
                                stockWarning.style.display = "block";
                                stockWarning.innerText = "Sản phẩm này đã hết hàng!";
                                addToCartBtn.disabled = true;
                            }

                            window.changeQuantity = function (amount) {
                                let currentQuantity = parseInt(quantityInput.value);
                                let newQuantity = currentQuantity + amount;

                                if (newQuantity < 1)
                                    newQuantity = 1;
                                if (newQuantity > stock)
                                    newQuantity = stock;

                                quantityInput.value = newQuantity;
                                cartQuantityInput.value = newQuantity;
                                validateQuantity();
                            };

                            function validateQuantity() {
                                let quantity = parseInt(quantityInput.value);
                                if (stock === 0 || quantity > stock) {
                                    stockWarning.style.display = "block";
                                    stockWarning.innerText = `Sản phẩm này đã hết hàng hoặc không đủ tồn kho!`;
                                    addToCartBtn.disabled = true;
                                    return false;
                                } else {
                                    stockWarning.style.display = "none";
                                    addToCartBtn.disabled = false;
                                    return true;
                                }
                            }
                        });
                    </script>
                </div>
            </div>

            <div class="row mt-5">
                <div class="col-12">
                    <h4>Thông Số Kĩ Thuật</h4>
                    ${product.description}
                </div>
            </div>

            <div class="row mt-5">
                <div class="col-md-8 mx-auto"> 
                    <h4>Thêm đánh giá</h4>
                    <form action="review" method="post" class="p-3 border rounded bg-light">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <div class="mb-3">
                            <label for="rating">Chọn số sao:</label>
                            <select name="rating" id="rating" class="form-select">
                                <c:forEach var="i" begin="1" end="5">
                                    <option value="${i}">${i} ★</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="comment">Nhận xét:</label>
                            <textarea name="comment" id="comment" class="form-control" rows="3" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Gửi đánh giá</button>
                    </form>
                </div>
            </div>

            <div class="row mt-4">
                <div class="col-md-8 mx-auto"> 
                    <h4>Đánh giá từ người dùng</h4>
                    <c:choose>
                        <c:when test="${not empty reviews}">
                            <ul class="list-group">
                                <c:forEach var="review" items="${reviews}">
                                    <li class="list-group-item">                            
                                        <strong>⭐ ${review.rating}/5</strong> - ${review.comment}
                                        <small class="text-muted">Ngày: ${review.createdAt}</small>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <p class="text-muted">Chưa có đánh giá nào.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <jsp:include page="/Common/footer.jsp"></jsp:include>
    </body>
</html>
