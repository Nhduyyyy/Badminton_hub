<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.User" %>
<%@ include file="/Common/navAdmin.jsp" %>

<%
    User profile = (User) request.getAttribute("profile");
    boolean isAdmin = false;
    if(profile != null && profile.getRole() != null){
        isAdmin = profile.getRole().getRoleName().equalsIgnoreCase("Admin");
    }
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Profile</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            :root {
                --primary: #7D0A0A;
                --secondary: #BF3131;
                --accent: #EAD196;
                --light: #EEEEEE;
                --text-color: #333;
                --error-bg: #f8d7da;
                --error-text: #721c24;
            }

            /* Reset & Base */
            * {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
            }
            body {
                background-color: var(--light);
                color: var(--text-color);
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;

                .profile-wrapper {
                    max-width: 800px;
                    margin: 40px auto;
                    background: #fff;
                    border: 1px solid var(--accent);
                    border-radius: 8px;
                    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                    padding: 30px;
                }
                h2 {
                    text-align: center;
                    color: var(--primary);
                    margin-bottom: 20px;
                }

                /* Form Styling */
                form {
                    display: flex;
                    flex-direction: column;
                    gap: 20px;
                }
                form label {
                    font-weight: bold;
                    color: var(--secondary);
                    margin-bottom: 5px;
                }
                form input[type="text"],
                form input[type="email"],
                form input[type="password"],
                form input[type="date"],
                form input[type="number"],
                form select {
                    width: 100%;
                    padding: 10px;
                    border: 1px solid var(--accent);
                    border-radius: 4px;
                    font-size: 1rem;
                }
                form input[type="file"] {
                    padding: 5px 0;
                }
                form input[type="submit"] {
                    background-color: var(--primary);
                    color: var(--light);
                    padding: 12px;
                    border: none;
                    border-radius: 4px;
                    font-size: 1.1rem;
                    cursor: pointer;
                    transition: background-color 0.3s;
                }
                form input[type="submit"]:hover {
                    background-color: var(--secondary);
                }
                /* Profile Info Blocks */
                .profile-info {
                    display: flex;
                    flex-direction: column;
                    gap: 10px;
                }
                .profile-info span {
                    font-weight: normal;
                }

                /* Error Box */
                .error-box {
                    position: fixed;
                    top: 20%;
                    right: 20px;
                    background-color: var(--error-bg);
                    color: var(--error-text);
                    border: 1px solid #f5c6cb;
                    padding: 15px;
                    border-radius: 4px;
                    box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.2);
                    width: 300px;
                    z-index: 1000;
                }

                /* Image & Spacing */
                form img {
                    margin-bottom: 10px;
                    border-radius: 50%;
                    border: 2px solid var(--accent);
                }

                .profile-container {
                    display: flex;
                    flex-wrap: wrap;
                    justify-content: space-between;
                }

                .profile-column {
                    width: 48%; /* Chia đôi màn hình, chừa khoảng cách */
                }

                .profile-avatar {
                    text-align: center; /* Căn giữa ảnh */
                    margin-bottom: 10px; /* Khoảng cách dưới */
                }

                .avatar-img {
                    width: 80px; /* Kích thước ảnh nhỏ lại */
                    height: 80px;
                    border-radius: 50%; /* Bo tròn avatar */
                    object-fit: cover; /* Hiển thị ảnh vừa khít */
                    border: 2px solid #ddd; /* Viền nhẹ */
                }

            </style>
        </head>
        <body>
            <div class="profile-wrapper">
                <h2>Profile</h2>

                <c:if test="${not empty errorMessage}">
                    <div class="error-box">
                        ${errorMessage}
                    </div>
                </c:if>

                <% if(request.getAttribute("successMessage") != null){ %>
                <p style="color:green;
                text-align:center;"><%= request.getAttribute("successMessage") %></p>
                <% } %>
                <% if(request.getAttribute("errorMessage") != null){ %>
                <p style="color:red;
                text-align:center;"><%= request.getAttribute("errorMessage") %></p>
                <% } %>

                <!-- Avatar phía trên tiêu đề -->
                <div class="profile-avatar text-center">
                    <label>Avatar:</label>
                    <img src="<%= profile.getAvatar() %>" alt="Avatar" width="100" height="100" />
                    <input type="file" name="avatar" />
                </div>



                <!--             Thông báo cập nhật 
                            <p class="text-center text-success">Cập nhật profile thành công.</p>-->

                <!-- Form -->
                <form action="updateProfile" method="post" enctype="multipart/form-data">
                    <div class="profile-container">
                        <div class="profile-column">
                            <div class="mb-3">
                                <label class="form-label">User ID:</label>
                                <input type="text" class="form-control" name="userId" value="<%= profile.getUserId() %>" readonly />
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Username:</label>
                                <input type="text" class="form-control" name="username" value="<%= profile.getUsername() %>" required />
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Email:</label>
                                <input type="email" class="form-control" name="email" value="<%= profile.getEmail() %>" required />
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Password:</label>
                                <input type="password" class="form-control" name="password" value="<%= profile.getPassword() %>" required />
                            </div>
                        </div>
                        <div class="profile-column">
                            <div class="mb-3">
                                <label class="form-label">Sex:</label>
                                <input type="text" class="form-control" name="sex" value="<%= profile.getSex() %>" required />
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Birth Date:</label>
                                <input type="date" class="form-control" name="birthDate" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(profile.getBirthDate()) %>" required />
                            </div>
                            <% if(isAdmin){ %>
                            <div class="mb-3">
                                <label>Score:</label>
                                <input type="number" name="score" value="<%= profile.getScore() %>" required />
                            </div>
                            <div class="profile-info">
                                <label>Locked:</label>
                                <select name="locked">
                                    <option value="false" <%= (!profile.isLocked()) ? "selected" : "" %>>False</option>
                                    <option value="true" <%= (profile.isLocked()) ? "selected" : "" %>>True</option>
                                </select>
                            </div>
                            <% } else { %>
                            <div class="profile-info">
                                <label>Score:</label>
                                <span><%= profile.getScore() %></span>
                            </div>
                            <div class="profile-info">
                                <label>Locked:</label>
                                <span><%= profile.isLocked() %></span>
                            </div>
                            <% } %>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <span class="fw-bold text-danger">Created At:</span>
                            <span>2025-03-19 23:51:41.07</span>
                        </div>
                        <div class="col-6">
                            <span class="fw-bold text-danger">Updated At:</span>
                            <span>2025-03-19 23:51:41.07</span>
                        </div>
                    </div>
            </div>


            <!-- Nút cập nhật -->
            <div class="text-center mt-3">
                <button type="submit" class="btn btn-primary">Cập nhật Profile</button>
            </div>
        </form>

    </div>
</body>
</html>
