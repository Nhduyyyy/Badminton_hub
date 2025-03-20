<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trang Chủ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
     <style>
        body, .container {
            margin: 0;
            padding: 0;
        }
        .carousel {
            margin-top: 0;
        }
        .carousel img {
            height: 75vh;
            object-fit: cover;
            width: 100vw;
        }
        .sp img {
            width: 300px;
            height: 300px;
            object-fit: cover;
        }
        .full-width-banner {
    width: 100vw;  /* Chiều rộng toàn màn hình */
    height:70vh;  /* Giảm chiều cao */
     /* Giữ tỷ lệ và cắt bớt nếu cần */
}
.card img {
    height: 300px; /* Đặt chiều cao cố định */
    object-fit: cover; /* Cắt ảnh để vừa với khung */
    width: 60%; /* Đảm bảo ảnh luôn điền đầy chiều rộng */
}
.card {
    background-color: rgba(255, 255, 255, 0.8); /* Làm nền mờ đi */
    border: 3px solid rgba(0, 0, 0, 0.5); /* Viền đậm hơn */
    border-radius: 20px; /* Bo góc */
    box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.2); /* Đổ bóng nhẹ */
    transition: transform 0.3s ease-in-out; /* Hiệu ứng khi di chuột */
}

.card:hover {
    transform: scale(1.05); /* Phóng to nhẹ khi hover */
}

.card img {
    border-radius: 20px 20px 0 0; /* Bo tròn góc trên */
}

    </style>
 
</head>
<body>
    <div class="header">
            <jsp:include page="/Common/header.jsp" />
    </div>
     <div id="bannerCarousel" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                      <img src="<%= request.getContextPath() %>/Images/Banner1.jpg" class="d-block w-100" alt="Banner 1">
                </div>
                <div class="carousel-item">
                    <img src="<%= request.getContextPath() %>/Images/b3.jpg" class="d-block w-100" alt="Banner 2">
                </div>
                <div class="carousel-item">
                    <img src="<%= request.getContextPath() %>/Images/b1.jpg" class="d-block w-100" alt="Banner 3">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#bannerCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#bannerCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    <div class="container mt-4">
        <!-- Banner Slider -->
       

        <!-- Danh mục sản phẩm -->
        <h2 class="text-center my-4">Sản phẩm cầu lông</h2>
         <div class="row text-center list">
    <div class="col-md-4 mb-3">
        <div class="card">
            <a href="productlist?cId=1">
                <img src="https://thethao365.com.vn/Data/upload/images/Product/Caulong/VotCauLong/Lining/T11-2022/Axforce%209/vot-cau-long-lining-axforce-9-dec.jpg" class="card-img-top" alt="Vợt cầu lông">
            </a>
            <div class="card-body">
                <h5 class="card-title">Vợt cầu lông</h5>
            </div>
        </div>
    </div>

    <div class="col-md-4 mb-3">
        <div class="card">
            <a href="productlist?cId=2">
                <img src="https://www.jreastmall.com/img/goods/S316/L/8208874_l.jpg" class="card-img-top" alt="Giày cầu lông">
            </a>
            <div class="card-body">
                <h5 class="card-title">Giày cầu lông</h5>
            </div>
        </div>
    </div>

    <div class="col-md-4 mb-3">
        <div class="card">
            <a href="productlist?cId=3">
                <img src="https://product.hstatic.net/200000099191/product/dsc08291_9c820d6759844fd0bffa324ed2695ace.jpg" class="card-img-top" alt="Quần cầu lông">
            </a>
            <div class="card-body">
                <h5 class="card-title">Quần cầu lông</h5>
            </div>
        </div>
    </div>

    <div class="col-md-4 mb-3">
        <div class="card">
            <a href="productlist?cId=4">
                <img src="https://luongsport.com/wp-content/uploads/2023/03/8017a-den.jpg" class="card-img-top" alt="Áo cầu lông">
            </a>
            <div class="card-body">
                <h5 class="card-title">Áo cầu lông</h5>
            </div>
        </div>
    </div>

    <div class="col-md-4 mb-3">
        <div class="card">
            <a href="productlist?cId=5">
                <img src="https://tuanvisport.com.vn/wp-content/uploads/2024/06/balo-cau-long-yonex-1408-gia-cong-4.jpg" class="card-img-top" alt="Balo cầu lông">
            </a>
            <div class="card-body">
                <h5 class="card-title">Balo cầu lông</h5>
            </div>
        </div>
    </div>

    <div class="col-md-4 mb-3">
        <div class="card">
            <a href="productlist?cId=6">
                <img src="https://th.bing.com/th/id/OIP.oNWlzrkWeWC27KkH6U9tBgHaHa?rs=1&pid=ImgDetMain" class="card-img-top" alt="Cầu lông">
            </a>
            <div class="card-body">
                <h5 class="card-title">Cầu lông</h5>
            </div>
        </div>
    </div>
</div>

           
    </div>
<!--                <div class="banner-bottom">
    <img src="<%= request.getContextPath() %>/Images/b2.jpg" 
         alt="Banner dưới" class="full-width-banner">
</div>-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <jsp:include page="/Common/footer.jsp"></jsp:include>
</body>
</html>
