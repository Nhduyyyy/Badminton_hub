<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" 
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" 
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoginCss.css">
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: #f4f4f4;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .login-container {
                background: #ffffff;
                border-radius: 8px;
                padding: 40px;
                max-width: 400px;
                width: 100%;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
                border-top: 5px solid #E64A19;
                text-align: center;
            }
            .login-container h2 {
                color: #E64A19;
                margin-bottom: 20px;
            }
            .login-container label {
                display: block;
                margin-top: 10px;
                text-align: left;
                font-weight: bold;
            }
            .login-container input[type="text"],
            .login-container input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            .remember-forgot {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin: 10px 0;
            }
            .forgot-password {
                color: #E64A19;
                text-decoration: none;
                font-size: 14px;
            }
            .forgot-password:hover {
                text-decoration: underline;
            }
            .login-container input[type="submit"] {
                width: 100%;
                padding: 10px;
                background: #E64A19;
                border: none;
                color: white;
                border-radius: 4px;
                cursor: pointer;
                margin-top: 10px;
            }
            .login-container input[type="submit"]:hover {
                background: #D84315;
            }
            .login-google {
                width: 100%;
                padding: 10px;
                background: white;
                border: 1px solid #ccc;
                color: #333;
                border-radius: 4px;
                cursor: pointer;
                margin-top: 10px;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .login-google img {
                width: 20px;
                height: 20px;
                margin-right: 10px;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <h2>Đăng nhập</h2>

            <c:if test="${not empty errorMessage}">
                <div class="error" style="color: red;">${errorMessage}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <label for="identifier">Tên đăng nhập hoặc Email:</label>
                <input type="text" id="identifier" name="identifier" value="${cookie.cuser.value}" required />

                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" value="${cookie.cpass.value}" required />

                <div class="remember-forgot">
                    <div class="remember-me">
                        <input type="checkbox" id="remember" name="rem" value="ON" ${(cookie.crem!=null?'checked':'')} />
                        <label for="remember">Remember me</label>
                    </div>
                    <a class="forgot-password" href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
                </div>

                <input type="submit" value="Đăng nhập" />
            </form>

            <button class="login-google" onclick="window.location.href = 'https://accounts.google.com/o/oauth2/auth?client_id=816671294539-je3as7mkpkaqo5m1pq2e6gmeegq634bu.apps.googleusercontent.com&redirect_uri=http://localhost:8080/badminton/LoginGoogleHandler&response_type=code&scope=openid%20email%20profile&prompt=consent'">
                <img src="https://cdn2.iconfinder.com/data/icons/social-icons-33/128/Google-1024.png" alt="Google Logo"> Đăng nhập với Google
            </button>
        </div>
    </body>
</html>
