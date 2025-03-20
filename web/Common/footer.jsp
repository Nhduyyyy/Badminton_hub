<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

<style>
.site-footer {
    background-color: #e64a19;
    color: white;
    padding: 40px 0;
    font-size: 14px;
    
}

.footer-container {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
}

.footer-column {
    width: 23%;
}

.footer-column h5 {
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 15px;
    text-transform: uppercase;
}

.footer-column p, .footer-column a {
    color: white;
    text-decoration: none;
    margin-bottom: 8px;
    display: block;
}

.footer-column a:hover {
    text-decoration: underline;
}

.footer-bottom {
    background-color: #d84315;
    text-align: center;
    padding: 10px 0;
    margin-top: 20px;
}

.footer-icons {
    display: flex;
    gap: 10px;
}

.footer-icons a {
    color: white;
    font-size: 20px;
}
</style>

<footer class="site-footer">
    <div class="footer-container">
        <div class="footer-column">
            <h5>VNB Sports</h5>
            <p>Hệ thống cửa hàng cầu lông hàng đầu với hơn 50 chi nhánh trên toàn quốc.</p>
            <p>"Cung cấp sản phẩm chất lượng nhất phục vụ người chơi thể thao."</p>
        </div>

        <div class="footer-column">
            <h5>Liên hệ</h5>
            <p>Hotline: 0977508430 | 0338000308</p>
            <p>Email: info@shopvnb.com</p>
            <p>Hợp tác: 0947342259 (Ms. Thảo)</p>
        </div>

        <div class="footer-column">
            <h5>Chính sách</h5>
            <a href="#">Vận chuyển</a>
            <a href="#">Đổi trả</a>
            <a href="#">Bảo hành</a>
            <a href="#">Điều khoản</a>
        </div>

        <div class="footer-column">
            <h5>Hướng dẫn</h5>
            <a href="#">Mua hàng</a>
            <a href="#">Thanh toán</a>
            <a href="#">Kiểm tra bảo hành</a>
            <a href="#">Kiểm tra đơn hàng</a>
            <div class="footer-icons">
                <a href="#"><i class="bi bi-facebook"></i></a>
                <a href="#"><i class="bi bi-youtube"></i></a>
                <a href="#"><i class="bi bi-instagram"></i></a>
            </div>
        </div>
    </div>
    <div class="footer-bottom">
        Công ty TNHH VNB SPORTS - 390/2 Hà Huy Giáp, Quận 12, TP.HCM
    </div>
</footer>
