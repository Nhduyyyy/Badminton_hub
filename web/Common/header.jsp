<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%@page import="entity.Cart"%>
<style>
    /* Header chính */
    .site-header {
        background: #fff;
        padding: 0;
        border-bottom: 2px solid #ddd;
        position: relative;
        z-index: 1000;
    }

    /* Phần trên của header */
    .header-top {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 30px;
        height: 100px;
    }

    /* Logo */
    .logo img {
        height: 150px;
    }

    /* Hotline */
    .hotline {
        font-size: 16px;
        font-weight: bold;
        color: #000;
        margin-right: 80px;
        display: flex;
        align-items: center;
    }
    .hotline i {
        color: #e64a19;
        margin-right: 5px;
    }
    .hotline span {
        color: #e64a19;
    }

    /* Search */
    .search-container {
        position: relative;
        width: 500px;
        margin-right: 35px;
    }
    .search-container input {
        width: 100%;
        padding: 8px 15px;
        border-radius: 5px;
        border: 1px solid #ccc;
        outline: none;
        height: 35px;
    }
    .search-container i {
        position: absolute;
        right: 10px;
        top: 50%;
        transform: translateY(-50%);
        color: #999;
        cursor: pointer;
    }

    /* Các icon ở header */
    .header-icons {
        display: flex;
        align-items: center;
        gap: 20px;
    }
    .header-icons a {
        text-decoration: none;
        color: black;
        font-size: 14px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        position: relative;
        /* Giữ kích thước cố định để căn chỉnh đều */
        min-width: 50px;
        min-height: 50px;
    }
    .header-icons a i {
        font-size: 20px;
        color: #e64a19;
        /* Thêm khoảng cách giữa icon và chữ */
        margin-bottom: 4px;
    }
    .header-icons a:hover {
        color: #e64a19;
    }

    /* Badge giỏ hàng */
    .cart-container {
        position: relative;
    }
    .cart-badge {
        position: absolute;
        top: -5px;
        right: -8px;
        background-color: red;
        color: white;
        font-size: 12px;
        font-weight: bold;
        width: 18px;
        height: 18px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    /* Navigation bar */
    .navbar {
        background-color: #e64a19;
    }
    .navbar-nav .nav-item .nav-link {
        color: #fff;
        font-size: 16px;
        font-weight: bold;
        padding: 12px 20px;
    }
    .navbar-nav .nav-item .nav-link:hover {
        background-color: rgba(255, 255, 255, 0.2);
        border-radius: 5px;
    }

    /* Dropdown user */
    .dropdown {
        position: relative;
        display: inline-block;
    }
    .dropdown-menu {
        display: none;
        position: absolute;
        right: 0;
        background-color: white;
        min-width: 150px;
        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        padding: 8px 0;
        list-style: none;
    }
    .dropdown-menu li {
        padding: 8px 16px;
    }
    .dropdown-menu li a {
        text-decoration: none;
        color: black;
        display: block;
    }
    .dropdown-menu li:hover {
        background-color: #f1f1f1;
    }
    .dropdown.active .dropdown-menu {
        display: block;
    }
    #ho{
        font-size: 30px;
    }
</style>
<%
      Cart cart = (Cart) session.getAttribute("cart");
      int totalQuantity = 0;
      if (cart != null) {
          totalQuantity = cart.getTotalProductQuantity();  // Phương thức này sẽ được tạo trong lớp Cart
      }
%>
<header class="site-header">
    <div class="header-top">
        <div class="logo">
            <a href="<%= request.getContextPath() %>/home">
                <img src="<%= request.getContextPath() %>/Images/logo.jpg" alt="Logo" width="200">
            </a>
        </div>

        <div class="hotline">
            <i class="bi bi-headset"></i> HOTLINE:
            <span>0977508430 | 0338000308</span>
        </div>

        <!-- Search -->
        <div class="search-container">
            <li class="nav-item">
                <form class="d-flex" action="searchA" method="GET">
                    <input oninput="searchByName(this)" class="form-control me-2" type="search" name="query" placeholder="Tìm kiếm sản phẩm...">          
                </form>
            </li>
        </div>

        <div class="header-icons">
            <a href="${pageContext.request.contextPath}/chat.jsp"><i class="fa-solid fa-binoculars"></i>Tra cứu </a>
            <c:choose>
                <c:when test="${empty sessionScope.user}">
                    <div class="dropdown">
                        <a class="dropdown-toggle" href="#" role="button" id="userDropdown">
                            <i class="fas fa-user" id="ho"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/login">Đăng Nhập</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/register">Đăng Ký</a></li>

                        </ul>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="dropdown">
                        <a class="dropdown-toggle" href="#" role="button" id="userDropdown" data-bs-toggle="dropdown">
                            <i class="fas fa-user"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Tài khoản của tôi</a></li>
                            <li><a class="dropdown-item" href="/badminton/orderhistory">Đơn mua</a></li>
                            <li><a class="dropdown-item" href="logout">Đăng Xuất</a></li>
                        </ul>
                    </div>
                </c:otherwise>
            </c:choose>
            <a href="/badminton/Cart/cart.jsp" class="cart-container">
                <i class="fa-solid fa-cart-shopping"></i>
                <span class="cart-badge"><%= totalQuantity %></span>
                GIỎ HÀNG 
            </a>
        </div>
    </div>

    <nav class="navbar navbar-expand-lg">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mx-auto">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a>
                    </li>
                    <c:forEach var="c" items="${listC}">
                        <li class="nav-item">
                            <a class="nav-link" href="/badminton/productlist?cId=${c.cId}">${c.cName}</a>
                        </li>
                    </c:forEach>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/we?view=contact">Liên Hệ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/we?view=about">Giới Thiệu</a>
                    </li>

                </ul>
            </div>
        </div>
    </nav>
    <script>
        var contextPath = window.location.origin + "/badminton";
        ; // Lấy đường dẫn gốc của ứng dụng

        function searchByName(param) {
            var txtSearch = param.value;
            $.ajax({
                url: contextPath + "/searchA",
                type: "GET",
                data: {query: txtSearch},
                success: function (data) {
                    document.getElementById("content").innerHTML = data;
                },
                error: function (xhr) {
                    console.error("Lỗi khi gọi AJAX:", xhr);
                }
            });

        }

        document.addEventListener("DOMContentLoaded", function () {
            var dropdown = document.querySelector(".dropdown");
            var toggle = document.querySelector("#userDropdown");

            toggle.addEventListener("click", function (event) {
                event.preventDefault();
                dropdown.classList.toggle("active");
            });

            document.addEventListener("click", function (event) {
                if (!dropdown.contains(event.target)) {
                    dropdown.classList.remove("active");
                }
            });
        });
    </script>
</header>
