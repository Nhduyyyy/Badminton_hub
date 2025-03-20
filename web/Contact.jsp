<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liên Hệ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .contact-container {
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
        .contact-info {
            margin-top: 20px;
        }
        .contact-info p {
            font-size: 16px;
        }
        .contact-form {
            margin-top: 30px;
        }
    </style>
    <jsp:include page="/Common/header.jsp"></jsp:include>
</head>
<body>
    
    <div class="container contact-container">
        <h2 class="text-center">Liên Hệ Với Chúng Tôi</h2>
        <p class="text-center">Nếu bạn có bất kỳ câu hỏi hoặc cần hỗ trợ, hãy liên hệ ngay!</p>

        <div class="contact-info">
            <h4>Thông Tin Liên Hệ:</h4>
            <p><strong>Địa chỉ:</strong> Số X, Đường Y, Quận Z, Thành phố Hồ Chí Minh</p>
            <p><strong>Hotline:</strong> 0123 456 789 (Hỗ trợ 24/7)</p>
            <p><strong>Email:</strong> support@caulongshop.vn</p>
            <p><strong>Facebook:</strong> <a href="https://facebook.com/caulongshop">fb.com/caulongshop</a></p>
            <p><strong>Zalo:</strong> 0123 456 789</p>
        </div>

        <div class="contact-form">
            <h4>Gửi Tin Nhắn</h4>
            <form action="contactSubmit" method="POST">
                <div class="mb-3">
                    <label class="form-label">Họ và Tên:</label>
                    <input type="text" class="form-control" name="name" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <input type="email" class="form-control" name="email" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Nội dung:</label>
                    <textarea class="form-control" name="message" rows="4" required></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Gửi</button>
            </form>
        </div>
    </div>
      <jsp:include page="/Common/footer.jsp"></jsp:include>
</body>
</html>

