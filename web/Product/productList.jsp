<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!DOCTYPE html>
<html lang="vi">
<head>
    <jsp:include page="/Common/header.jsp"></jsp:include>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách sản phẩm</title>
    <style>
  .container {
    display: flex;
    max-width: 1200px;
    margin: 20px auto;
    gap: 20px;
}

/* Sidebar bên trái */
.sidebar {
    width: 250px;
    background: white;
    padding: 15px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    height: fit-content; /* Để không kéo dài quá mức */
}

/* Phần sản phẩm */
.products {
    flex: 1;
    background: white;
    padding: 15px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

/* Hiển thị 3 sản phẩm trên mỗi hàng */
.product-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr); /* 3 sản phẩm mỗi hàng */
    gap: 20px;
}

/* Card sản phẩm */
.product-item {
    background: white;
    padding: 10px;
    text-align: center;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s;
}

.product-item:hover {
    transform: scale(1.05); /* Hiệu ứng hover */
}

/* Hình ảnh sản phẩm */
.product-item img {
    max-width: 100%;
    height: 200px; /* Giới hạn chiều cao ảnh */
    object-fit: cover; /* Giữ tỷ lệ ảnh */
    border-radius: 5px;
}

/* Giá sản phẩm */
.product-item .price {
    color: red;
    font-weight: bold;
    font-size: 16px;
}
#filterForm {
    background: rgba(248, 249, 250, 0.8); /* Nền sáng nhẹ, có chút trong suốt */
    padding: 15px;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    width: 250px;
    backdrop-filter: blur(5px); /* Tạo hiệu ứng mờ nhẹ */
}

#filterForm h3 {
    font-size: 18px;
    color: #007bff;
    margin-bottom: 10px;
}

#filterForm input[type="checkbox"] {
    margin-right: 8px;
}

#filterForm label {
    font-size: 16px;
    color: #333;
    cursor: pointer;
}

#filterForm br {
    margin-bottom: 8px;
}


    </style>
</head>
<body>
    
    <body>
    <div class="container">
        <!-- Sidebar (Bộ lọc) -->
     <form id="filterForm">
    <input type="hidden" name="cid" id="categoryId" value="${param.cid}">
    <h3>Chọn mức giá</h3>
    <input type="checkbox" name="price" value="0-500000" onchange="filterProducts()"> Dưới 500.000đ <br>
    <input type="checkbox" name="price" value="500000-1000000" onchange="filterProducts()"> 500.000đ - 1 triệu <br>
    <input type="checkbox" name="price" value="1000000-2000000" onchange="filterProducts()"> 1 - 2 triệu <br>
    <input type="checkbox" name="price" value="2000000-3000000" onchange="filterProducts()"> 2 - 3 triệu <br>
    <input type="checkbox" name="price" value="3000000-99999999" onchange="filterProducts()"> Trên 3 triệu <br>

    <h3>Thương hiệu</h3>
    <input type="checkbox" name="brand" value="1" onchange="filterProducts()"> Apacs <br>
    <input type="checkbox" name="brand" value="2" onchange="filterProducts()"> Lining <br>
    <input type="checkbox" name="brand" value="3" onchange="filterProducts()"> Victor <br>
    <input type="checkbox" name="brand" value="4" onchange="filterProducts()"> Yonex <br>
</form>

        <!-- Danh sách sản phẩm -->
        <div class="products">
                    <div id="content" class="product-grid">
                    <c:forEach var="p" items="${listP}">
                        <div class="product-item">
                            <a href="/badminton/productdetail?productId=${p.productId}" >
                                <img src="${p.image}" alt="${p.productName}"></a>
                                <p>${p.productName}</p>
                                <p class="price">${p.price} đ</p>
                            
                        </div>
                    </c:forEach>
                </div>
            </div>
    </div>
  <script>
    function filterProducts() {
        var formData = $("#filterForm").serialize();

        $.ajax({
            url: "productListServlet",
            type: "GET",
            data: formData,
            success: function (data) {
                $("#content").html(data);
            },
            error: function (xhr) {
                console.error("Lỗi khi tải sản phẩm:", xhr);
            }
        });
    }
</script>

</body>
    <jsp:include page="/Common/footer.jsp"></jsp:include>
</html>