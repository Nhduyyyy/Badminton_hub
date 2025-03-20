<%-- 
    Document   : updateUser
    Created on : 4 thg 3, 2025, 18:35:03
    Author     : nhatduy261179
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chỉnh sửa người dùng</title>
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

            /* Wrapper thay cho body để không ghi đè CSS hiện có */
            .updateUser-wrapper {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: linear-gradient(135deg, var(--non-photo-blue-2), var(--light-cyan));
                padding: 40px;
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            /* Card container cho form */
            .updateUser-container {
                background: #fff;
                padding: 30px 40px;
                border-radius: 8px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                border-top: 5px solid var(--marian-blue);
                width: 450px;
            }

            .updateUser-container h1 {
                text-align: center;
                margin-bottom: 20px;
                color: var(--marian-blue);
                font-size: 1.75rem;
            }

            /* Các nhóm form */
            .form-group {
                margin-bottom: 15px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
                color: var(--federal-blue);
            }

            .form-group input,
            .form-group select {
                width: 100%;
                padding: 10px;
                border: 1px solid var(--non-photo-blue);
                border-radius: 4px;
                font-size: 1rem;
                transition: border-color 0.3s, box-shadow 0.3s;
            }

            .form-group input:focus,
            .form-group select:focus {
                border-color: var(--pacific-cyan);
                box-shadow: 0 0 5px rgba(0, 180, 216, 0.5);
                outline: none;
            }

            /* Hiển thị lỗi */
            .error {
                color: var(--federal-blue);
                background-color: #ffecec;
                border: 1px solid #ff5c5c;
                padding: 10px;
                border-radius: 4px;
                margin-bottom: 20px;
                text-align: center;
            }

            /* Nút submit */
            .updateUser-container button {
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

            .updateUser-container button:hover {
                background-color: var(--pacific-cyan);
                transform: translateY(-2px);
            }
        </style>
    </head>
    <body>
        <div class="updateUser-wrapper">
            <div class="updateUser-container">
                <h1>Chỉnh sửa người dùng</h1>

                <c:if test="${not empty errorMessage}">
                    <div class="error">${errorMessage}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/updateUser" method="post" enctype="multipart/form-data">
                    <!-- Sử dụng input hidden để gửi userId -->
                    <input type="hidden" name="userId" value="${updateUser.userId}" />

                    <div class="form-group">
                        <label>Tên đăng nhập:</label>
                        <input type="text" name="username" value="${updateUser.username}" required />
                    </div>

                    <div class="form-group">
                        <label>Email:</label>
                        <input type="email" name="email" value="${updateUser.email}" required />
                    </div>

                    <div class="form-group">
                        <label>Mật khẩu:</label>
                        <input type="password" name="password" value="${updateUser.password}" required />
                    </div>

                    <div class="form-group">
                        <label>Họ tên:</label>
                        <input type="text" name="fullName" value="${updateUser.fullName}" required />
                    </div>

                    <div class="form-group">
                        <label>Số điện thoại:</label>
                        <input type="text" name="phoneNumber" value="${updateUser.phoneNumber}" />
                    </div>

                    <div class="form-group">
                        <label>Vai trò:</label>
                        <select name="roleId">
                            <option value="1" ${updateUser.role.roleId == 1 ? 'selected' : ''}>Admin</option>
                            <option value="2" ${updateUser.role.roleId == 2 ? 'selected' : ''}>Manager</option>
                            <option value="3" ${updateUser.role.roleId == 3 ? 'selected' : ''}>User</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Avatar:</label>
                        <input type="file" name="avatar" value="${updateUser.avatar}" />
                    </div>

                    <div class="form-group">
                        <label>Score:</label>
                        <input type="number" name="score" value="${updateUser.score}" />
                    </div>

                    <div class="form-group">
                        <label>Giới tính:</label>
                        <select name="sex">
                            <option value="Nam" ${updateUser.sex == 'Nam' ? 'selected' : ''}>Nam</option>
                            <option value="Nữ" ${updateUser.sex == 'Nữ' ? 'selected' : ''}>Nữ</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Ngày sinh:</label>
                        <input type="date" name="birthDate" value="${updateUser.birthDate}" />
                    </div>

                    <div class="form-group">
                        <label>Tình trạng:</label>
                        <select name="locked">
                            <option value="false" ${!updateUser.locked ? 'selected' : ''}>Hoạt động</option>
                            <option value="true" ${updateUser.locked ? 'selected' : ''}>Khóa</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <button type="submit">Cập nhật</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
