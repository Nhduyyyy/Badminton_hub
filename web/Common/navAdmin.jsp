
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>nav</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" 
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" 
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            body {
                /*                font-family: Arial, sans-serif;
                                background-color: #f8f9fa;*/
                text-align: center;
                margin: 0;
                padding: 0;
            }
            .custom-navbar {
                width: 100%;
                background-color: #023e8a; /* Màu cam */

            }

            .navbar-nav {
                width: 100%;
                display: flex;
                justify-content: space-evenly; /* Cách đều nhau */
                align-items: center; /* Căn giữa theo chiều dọc */
                padding: 0;
            }

            .nav-item {
                list-style: none;
            }

            .navbar-brand {
                color: white !important;
                font-weight: bold;
                text-transform: uppercase;
                padding: 10px;
            }

            .nav-link {
                color: white !important;
                font-weight: bold;
                text-transform: uppercase;
                padding: 10px 15px;
            }

            .navbar-nav .dropdown-menu {
                background-color: #E6511D; /* Màu nền dropdown */
            }

            .navbar-nav .dropdown-item {
                color: white;
            }

            .navbar-nav .dropdown-item:hover {
                background-color: #cc3e1a; /* Màu hover */
            }


        </style>
    </head>
    <body>
        <div class="navAdmin-wrapper">
            <nav class="navbar navbar-expand-lg custom-navbar">
                <div class="container-fluid">
                    <a class="navbar-brand" >${sessionScope.user.role.roleName}</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
                            aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNavDropdown">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/home">Home</a>
                            </li>
                            <c:choose>
                                <c:when test="${sessionScope.user.role.roleName eq 'Admin'}">
                                    <li class="nav-item">
                                        <a class="nav-link" href="${pageContext.request.contextPath}/userManagement">User</a>
                                    </li>
                                    <li class="nav-item dropdown">
                                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                           aria-expanded="false">
                                            Product Manager
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/productlistadmin">Product</a></li>
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/brandList">Brand</a></li>
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/categoryList">Category</a></li>
                                        </ul>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="${pageContext.request.contextPath}/displayLoginCount">Count User</a>
                                    </li>
                                </c:when>
                                <c:when test="${sessionScope.user.role.roleName eq 'Manager'}">
                                    <li class="nav-item dropdown">
                                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                           aria-expanded="false">
                                            Product Manager
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/productlistadmin">Product</a></li>
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/brandList">Brand</a></li>
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/categoryList">Category</a></li>
                                        </ul>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="nav-item">
                                        <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    </body>
</html>