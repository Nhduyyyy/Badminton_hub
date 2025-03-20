<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.Brand" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Brand Form</title>
        <style>
            :root {
                --federal-blue: #03045eff;
                --marian-blue: #023e8aff;
                --honolulu-blue: #0077b6ff;
                --pacific-cyan: #00b4d8ff;
                --non-photo-blue: #90e0efff;
                --non-photo-blue-2: #ade8f4ff;
                --light-cyan: #caf0f8ff;
                --background-gradient: linear-gradient(135deg, var(--non-photo-blue-2), var(--light-cyan));
            }
            body {
                margin: 0;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: var(--background-gradient);
            }
            .form-wrapper {
                max-width: 500px;
                margin: 40px auto;
                padding: 30px;
                background: #fff;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            }
            .form-wrapper h1 {
                text-align: center;
                color: var(--marian-blue);
                margin-bottom: 20px;
            }
            form label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
                color: var(--federal-blue);
            }
            form input[type="text"] {
                width: 100%;
                padding: 10px;
                border: 1px solid var(--non-photo-blue);
                border-radius: 4px;
                margin-bottom: 15px;
                font-size: 1rem;
            }
            form input[type="submit"] {
                width: 100%;
                padding: 10px;
                border: none;
                border-radius: 4px;
                background-color: var(--honolulu-blue);
                color: #fff;
                font-size: 1.1rem;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            form input[type="submit"]:hover {
                background-color: var(--pacific-cyan);
            }
            .back-link {
                display: block;
                text-align: center;
                margin-top: 20px;
                color: var(--honolulu-blue);
                text-decoration: none;
            }
            .back-link:hover {
                color: var(--pacific-cyan);
            }
        </style>
    </head>
    <body>
        <div class="form-wrapper">
            <h1><%= request.getAttribute("brand") == null ? "Add New Brand" : "Edit Brand" %></h1>
            <form action="<%= request.getContextPath() %>/brandForm" method="post">
                <%
                  Brand brand = (Brand) request.getAttribute("brand");
                  if (brand != null) {
                %>
                <input type="hidden" name="id" value="<%= brand.getBrandId() %>" />
                <% } %>
                <label for="brandName">Brand Name:</label>
                <input type="text" id="brandName" name="brandName" value="<%= brand != null ? brand.getBrandName() : "" %>" required />
                <input type="submit" value="Save" />
            </form>
            <a href="<%= request.getContextPath() %>/brandList" class="back-link">Back to Brand List</a>
        </div>
    </body>
</html>
