<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giới Thiệu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .about-container {
            max-width: 900px;
            margin: 30px auto;
            padding: 20px;
            background: #f8f9fa;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #023e8a;
        }
        .about-content {
            font-size: 16px;
            line-height: 1.8;
        }
    </style>
</head>
<body>
     <jsp:include page="/Common/header.jsp"></jsp:include>
    <div class="container about-container">
        <h2 class="text-center">Giới Thiệu Về Chúng Tôi</h2>
        <p class="text-center"><em>Chào mừng bạn đến với cửa hàng cầu lông uy tín hàng đầu!</em></p>

        <div class="about-content">
            <p><strong>[BadmintionHub]</strong> là địa chỉ tin cậy dành cho các tín đồ đam mê cầu lông. Chúng tôi cung cấp các sản phẩm chất lượng cao từ các thương hiệu nổi tiếng như Yonex, Lining, Victor, Mizuno, cùng nhiều dòng sản phẩm phù hợp với mọi nhu cầu từ người mới chơi đến vận động viên chuyên nghiệp.</p>

            <h4>Sứ Mệnh</h4>
            <p>Chúng tôi cam kết mang đến cho khách hàng những sản phẩm tốt nhất với giá cả hợp lý, đi kèm với dịch vụ chăm sóc khách hàng tận tâm. Không chỉ đơn thuần là một cửa hàng, chúng tôi còn mong muốn xây dựng một cộng đồng yêu cầu lông, nơi mọi người có thể chia sẻ kinh nghiệm, học hỏi và phát triển kỹ năng.</p>

            <h4>Vì Sao Chọn Chúng Tôi?</h4>
            <ul>
                <li><strong>100% Hàng Chính Hãng:</strong> Cam kết sản phẩm chính hãng từ nhà sản xuất.</li>
                <li><strong>Giá Cả Cạnh Tranh:</strong> Giá tốt nhất trên thị trường với nhiều ưu đãi hấp dẫn.</li>
                <li><strong>Dịch Vụ Chăm Sóc Khách Hàng:</strong> Tư vấn tận tình, hỗ trợ đổi trả nhanh chóng.</li>
                <li><strong>Giao Hàng Toàn Quốc:</strong> Nhận hàng nhanh chóng và đảm bảo an toàn.</li>
            </ul>

            <h4>Kết Nối Với Chúng Tôi</h4>
            <p>Hãy theo dõi <strong>[BadmintionHub]</strong> trên mạng xã hội để cập nhật những tin tức mới nhất về cầu lông, sản phẩm hot và các chương trình khuyến mãi.</p>
        </div>
    </div>
</body>
  <jsp:include page="/Common/footer.jsp"></jsp:include>
</html>

