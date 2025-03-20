<%-- 
    Document   : adminManageUsers
    Created on : 4 thg 3, 2025, 13:45:07
    Author     : nhatduy261179
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/Common/navAdmin.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Manage Users</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" 
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" 
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminManageListCss.css">
        <style>
            /* ----- 1. Layout chung ----- */
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 0;
            }

            .admin-wrapper {
                background: white;
            }

            h1, h2 {
                text-align: center;
                color: #333;
            }


            #searchForm {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin-bottom: 20px;
            }

            #query {
                width: 300px;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }

            button {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 15px;
                cursor: pointer;
                border-radius: 5px;
            }

            button:hover {
                background-color: #0056b3;
            }


            .create-user-btn {
                display: block;
                width: fit-content;
                margin: 10px auto;
                padding: 10px 15px;
                background-color: #28a745;
                color: white;
                text-decoration: none;
                font-weight: bold;
                border-radius: 5px;
                text-align: center;
            }

            .create-user-btn:hover {
                background-color: #218838;
            }


            .filter-form {
                display: flex;
                justify-content: center;
                gap: 10px;
                flex-wrap: wrap;
                margin-bottom: 20px;
            }

            .filter-form select,
            .filter-form input[type="submit"] {
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }


            .table {
                width: 100%;
                border-collapse: collapse;
                background: white;
            }

            .table-wrapper {

                margin: auto;
                overflow-x: auto; /* Thêm thanh cuộn ngang nếu cần */
                background: white;
                border-radius: 8px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            }

            /* ----- 2. Cố định header bảng ----- */
            .table thead {
                position: sticky;
                top: 0;
                background: #007bff; /* Giữ nguyên màu header */
                color: white;
                z-index: 10;
            }

            .table th, .table td {
                padding: 10px;
                border: 1px solid #ddd;
                text-align: center;
            }

            .table tr:nth-child(even) {
                background: #f2f2f2;
            }



            .table th {
                background: #007bff;
                color: white;
            }



            /* ----- 7. Nút hành động ----- */
            .btn {
                padding: 5px 10px;
                border-radius: 5px;
                text-decoration: none;
                font-size: 14px;
            }

            .btn-outline-danger {
                color: #dc3545;
                border: 1px solid #dc3545;
            }

            .btn-outline-danger:hover {
                background: #dc3545;
                color: white;
            }

            .btn-outline-warning {
                color: #ffc107;
                border: 1px solid #ffc107;
            }

            .btn-outline-warning:hover {
                background: #ffc107;
                color: black;
            }

            .btn-outline-primary {
                color: #007bff;
                border: 1px solid #007bff;
            }

            .btn-outline-primary:hover {
                background: #007bff;
                color: white;
            }

            .btn-outline-secondary {
                color: #6c757d;
                color: white;
            }

            /* ----- 8. Phân trang ----- */
            .pagination {
                text-align: center;
                margin-top: 20px;
            }

            .pagination a, .pagination strong {
                display: inline-block;
                padding: 8px 12px;
                margin: 2px;
                border-radius: 5px;
                text-decoration: none;
                font-weight: bold;
            }

            .pagination a {
                background: #007bff;
                color: white;
            }

            .pagination a:hover {
                background: #0056b3;
            }

            .pagination strong {
                background: #333;
                color: white;
            }

        </style>
    </head>
    <body>
        <div class="admin-wrapper">
            <h2>Tìm kiếm người dùng</h2>
            <form id="searchForm" action="${pageContext.request.contextPath}/userManagement" method="get">
                <input id="query" type="text" name="query" placeholder="Nhập từ khóa..."
                       value="${param.query}" onkeyup="checkSearchInput()" />
                <button type="submit">Tìm kiếm</button>
            </form>

            <h1>Tạo người dùng mới</h1>
            <a class="create-user-btn" href="${pageContext.request.contextPath}/createUser">Create</a>

            <!-- Form lọc theo vai trò, trạng thái và số bản ghi trên 1 trang -->
            <form class="filter-form" action="${pageContext.request.contextPath}/userManagement" method="get">
                <label for="roleFilter">Chọn vai trò:</label>
                <select name="roleFilter" id="roleFilter">
                    <option value="all" ${roleFilter == 'all' ? 'selected' : ''}>Tất cả</option>
                    <option value="admin" ${roleFilter == 'admin' ? 'selected' : ''}>Admin</option>
                    <option value="manager" ${roleFilter == 'manager' ? 'selected' : ''}>Manager</option>
                    <option value="user" ${roleFilter == 'user' ? 'selected' : ''}>User</option>
                </select>

                <label for="statusFilter">Chọn trạng thái:</label>
                <select name="statusFilter" id="statusFilter">
                    <option value="all" ${statusFilter == 'all' ? 'selected' : ''}>Tất cả</option>
                    <option value="active" ${statusFilter == 'active' ? 'selected' : ''}>Active</option>
                    <option value="locked" ${statusFilter == 'locked' ? 'selected' : ''}>Locked</option>
                </select>

                <label for="pageSize">Số bản ghi trên mỗi trang:</label>
                <select name="pageSize" id="pageSize">
                    <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                    <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                    <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
                    <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
                </select>

                <input type="submit" value="Lọc">
            </form>

            <table class="table admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Ảnh</th>
                        <th>Tên người dùng</th>
                        <th>Email</th>
                        <th>Password</th>
                        <th>Họ và tên</th>
                        <th>Số điện thoại</th>
                        <th>Birth Date</th>
                        <th>Ngày tạo</th>
                        <th>Ngày cập nhật</th>
                        <th>Đã khóa</th>                   
                        <th>ID Vai trò</th>
                        <th>Tên vai trò</th>
                        <th colspan="3">Action</th>
                    </tr>
                </thead>
                <tbody class="table-group-divider">
                    <c:set var="fakeId" value="${(currentPage - 1) * pageSize}" />
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td>${fakeId + 1}</td>
                            <td>
                                <img src="${pageContext.request.contextPath}/${user.avatar}" alt="User Image" width="50" height="50"/>
                            </td>
                            <td>${user.username}</td>
                            <td>${user.email}</td>
                            <td>${user.password}</td>
                            <td>${user.fullName}</td>
                            <td>${user.phoneNumber}</td>
                            <td>
                                <fmt:formatDate value="${user.birthDate}" pattern="yyyy-MM-dd"></fmt:formatDate>
                                </td>
                                <td>
                                <fmt:formatDate value="${user.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                </td>
                                <td>
                                <fmt:formatDate value="${user.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                </td>
                                <td>
                                <c:choose>
                                    <c:when test="${user.locked}">khóa</c:when>
                                    <c:otherwise>hoat động</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${user.role.roleId}</td>
                            <td>${user.role.roleName}</td>
                            <td>
                                <a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/deleteUser?id=${user.userId}" onclick="return confirm('Bạn có chắc muốn xóa?');">Xóa</a>
                            </td>
                            <td>
                                <a class="btn btn-outline-warning" href="${pageContext.request.contextPath}/updateUser?userId=${user.userId}">Update</a>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.locked}">
                                        <!-- Form mở khóa -->
                                        <form action="${pageContext.request.contextPath}/lockUnlock" method="post" style="display:inline;">
                                            <input type="hidden" name="id" value="${user.userId}" />
                                            <input type="hidden" name="action" value="unlock" />
                                            <button type="submit" class="btn btn-outline-primary">Mở khóa</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Form khóa -->
                                        <form action="${pageContext.request.contextPath}/lockUnlock" method="post" style="display:inline;">
                                            <input type="hidden" name="id" value="${user.userId}" />
                                            <input type="hidden" name="action" value="lock" />
                                            <button type="submit" class="btn btn-outline-secondary">Khóa</button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:set var="fakeId" value="${fakeId + 1}" />
                    </c:forEach>
                </tbody>
            </table>

            <!-- Pagination -->
            <div class="pagination">
                <c:if test="${totalPages > 1}">
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <strong>${i}</strong>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/userManagement?page=${i}&roleFilter=${roleFilter}&statusFilter=${statusFilter}&pageSize=${pageSize}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
            </div>
            <script>
                // Tự động submit form tìm kiếm nếu ô input bị xóa hết
                function checkSearchInput() {
                    var input = document.getElementById("query");
                    if (input.value.trim() === "") {
                        document.getElementById("searchForm").submit();
                    }
                }
            </script>
        </div>
    </body>
</html>
