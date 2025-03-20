<%-- 
    Document   : forgotPassword
    Created on : 20 thg 3, 2025, 01:00:56
    Author     : nhatduy261179
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Quên mật khẩu</title>
        <style>
            /* Đặt lại một số thiết lập mặc định */
            * {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
            }

            /* Thiết lập cho toàn bộ body */
            body {
                font-family: Arial, sans-serif;
                background: linear-gradient(to right, #6a11cb, #2575fc);
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh; /* Chiều cao 100% của viewport */
            }

            /* Khung chứa form đăng nhập */
            .login-container {
                background: #fff;
                border-radius: 8px;
                padding: 40px;
                width: 350px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                text-align: center;
            }

            /* Tiêu đề */
            .login-container h2 {
                margin-bottom: 20px;
                color: #333;
            }

            /* Cấu hình form và các thành phần bên trong */
            .login-container form {
                display: flex;
                flex-direction: column;
                gap: 15px;
            }

            .login-container label {
                font-size: 16px;
                color: #555;
                text-align: left;
            }

            .login-container input[type="email"],
            .login-container input[type="submit"] {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            /* Nút submit */
            .login-container input[type="submit"] {
                background-color: #2575fc;
                color: #fff;
                cursor: pointer;
                border: none;
                transition: background-color 0.3s ease;
            }

            .login-container input[type="submit"]:hover {
                background-color: #6a11cb;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <h2>Quên mật khẩu</h2>
            <c:if test="${not empty errorMessage}">
                <p style="color:red;">${errorMessage}</p>
            </c:if>
            <c:if test="${not empty message}">
                <p style="color:green;">${message}</p>
            </c:if>
            <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                <label for="email">Nhập Email:</label>
                <input type="email" id="email" name="email" required/>
                <input type="submit" value="Gửi"/>
            </form>
        </div>
    </body>
</html>

