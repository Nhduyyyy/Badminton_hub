<%-- 
    Document   : createUser
    Created on : 4 thg 3, 2025, 20:51:01
    Author     : nhatduy261179
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tạo Người Dùng Mới</title>
        <style>
            :root {
                --federal-blue: #03045eff;
                --marian-blue: #023e8aff;
                --honolulu-blue: #0077b6ff;
                --blue-green: #0096c7ff;
                --pacific-cyan: #00b4d8ff;
                --vivid-sky-blue: #48cae4ff;
                --non-photo-blue: #90e0efff;
                --non-photo-blue-2: #ade8f4ff;
                --light-cyan: #caf0f8ff;
            }

            /* Wrapper thay cho body */
            .createUser-wrapper {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: linear-gradient(135deg, var(--non-photo-blue-2), var(--light-cyan));
                padding: 40px;
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            /* Container của form được thiết kế như một card hiện đại */
            .createUser-container {
                background: #fff;
                padding: 30px 40px;
                border-radius: 8px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                border-top: 5px solid var(--marian-blue);
                width: 400px;
            }

            .createUser-container h1 {
                text-align: center;
                margin-bottom: 20px;
                color: var(--marian-blue);
                font-size: 1.75rem;
            }

            .createUser-container .form-group {
                margin-bottom: 15px;
            }

            .createUser-container label {
                display: block;
                margin-bottom: 5px;
                color: var(--federal-blue);
                font-weight: bold;
            }

            .createUser-container input,
            .createUser-container select {
                width: 100%;
                padding: 10px;
                border: 1px solid var(--non-photo-blue);
                border-radius: 4px;
                font-size: 1rem;
                transition: border-color 0.3s, box-shadow 0.3s;
            }

            .createUser-container input:focus,
            .createUser-container select:focus {
                border-color: var(--pacific-cyan);
                box-shadow: 0 0 5px rgba(0, 180, 216, 0.5);
                outline: none;
            }

            .createUser-container .error {
                color: var(--federal-blue);
                background-color: #ffecec;
                border: 1px solid #ff5c5c;
                padding: 10px;
                border-radius: 4px;
                margin-bottom: 20px;
                text-align: center;
            }

            .createUser-container button {
                width: 100%;
                padding: 12px;
                border: none;
                border-radius: 4px;
                background-color: var(--honolulu-blue);
                color: #fff;
                font-size: 1.1rem;
                cursor: pointer;
                transition: background-color 0.3s, transform 0.3s;
            }

            .createUser-container button:hover {
                background-color: var(--pacific-cyan);
                transform: translateY(-2px);
            }
        </style>
    </head>
    <body>
        <div class="createUser-wrapper">
            <div class="createUser-container">
                <h1>Tạo Người Dùng Mới</h1>

                <!-- Hiển thị thông báo lỗi nếu có -->
                <c:if test="${not empty errorMessages}">
                    <div class="error">${errorMessages}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/createUser" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="text" name="username" id="username" required/>
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" name="email" id="email" required/>
                    </div>
                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" name="password" id="password" required/>
                    </div>
                    <div class="form-group">
                        <label for="fullName">Họ và tên:</label>
                        <input type="text" name="fullName" id="fullName" required/>
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber">Số điện thoại:</label>
                        <input type="text" name="phoneNumber" id="phoneNumber" required/>
                    </div>
                    <div class="form-group">
                        <label for="roleId">Vai trò:</label>
                        <select name="roleId" id="roleId">
                            <option value="1">Admin</option>
                            <option value="2">Manager</option>
                            <option value="3">User</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="locked">Khóa tài khoản:</label>
                        <select name="locked" id="locked">
                            <option value="false">Hoạt Động</option>
                            <option value="true">Khóa</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="Avatar">Avatar:</label>
                        <input type="file" name="Avatar" id="Avatar" placeholder="default.gif nếu để trống"/>
                    </div>
                    <div class="form-group">
                        <label for="sex">Giới tính:</label>
                        <select name="sex" id="sex">
                            <option value="Male">Nam</option>
                            <option value="Female">Nữ</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="birthDate">Ngày sinh:</label>
                        <input type="date" name="birthDate" id="birthDate" required/>
                    </div>
                    <div class="form-group">
                        <label for="score">Điểm số:</label>
                        <input type="number" name="score" id="score" required/>
                    </div>
                    <div class="form-group">
                        <button type="submit">Tạo Người Dùng</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
