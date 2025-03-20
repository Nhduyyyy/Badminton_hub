<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng ký tài khoản</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

        <!-- Bootstrap Bundle JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" 
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" 
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/RegisterCss.css">

        <style>
            .error-message {
                color: red;
                font-weight: bold;
                text-align: center;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/Common/header.jsp"></jsp:include>

        <div class="register-wrapper">
            <form class="register-form" action="register" method="post" enctype="multipart/form-data">
                <h1>Đăng ký tài khoản</h1>

                <div class="form-group">
                    <label for="username">Tên đăng nhập:</label>
                    <input type="text" id="username" name="username" required />
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required />
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu:</label>
                    <input type="password" id="password" name="password" required />
                </div>
                <div class="form-group">
                    <label for="fullName">Họ và tên:</label>
                    <input type="text" id="fullName" name="fullName" required />
                </div>
                <div class="form-group">
                    <label for="sex">Giới tính:</label>
                    <select id="sex" name="sex" required>
                        <option value="Male">Nam</option>
                        <option value="Female">Nữ</option>
                        <option value="Other">Khác</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="birthDate">Ngày sinh:</label>
                    <input type="date" id="birthDate" name="birthDate" required />
                </div>
                <div class="form-group">
                    <label for="Avatar">Avatar:</label>
                    <input type="file" name="Avatar" id="Avatar" placeholder="default.gif nếu để trống"/>
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Số điện thoại (tùy chọn):</label>
                    <input type="text" id="phoneNumber" name="phoneNumber" />
                </div>
                <div class="form-group">
                    <button type="submit">Đăng ký</button>
                </div>

                <!-- Hiển thị thông báo lỗi phía dưới nút Đăng ký -->
                <c:if test="${not empty errorMessage}">
                    <div class="error-message">${errorMessage}</div>
                </c:if>

            </form>
        </div>

        <jsp:include page="/Common/footer.jsp"></jsp:include>
    </body>
</html>
