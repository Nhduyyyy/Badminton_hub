<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, entity.Category" %>
<%@ include file="/Common/navAdmin.jsp" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Category List</title>
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
            .admin-wrapper {
                max-width: 1200px;
                margin: 40px auto;
                padding: 20px;
                background: #fff;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }
            h1 {
                text-align: center;
                color: var(--marian-blue);
                margin-bottom: 20px;
            }
            .action-link {
                display: inline-block;
                margin-bottom: 20px;
                padding: 10px 20px;
                border: 2px solid var(--honolulu-blue);
                border-radius: 4px;
                color: var(--honolulu-blue);
                text-decoration: none;
                transition: background-color 0.3s, color 0.3s;
            }
            .action-link:hover {
                background-color: var(--honolulu-blue);
                color: #fff;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background: #fff;
                border-radius: 8px;
                overflow: hidden;
                margin-bottom: 20px;
            }
            th, td {
                padding: 12px 15px;
                text-align: center;
                border-bottom: 1px solid #ddd;
            }
            th {
                background-color: var(--marian-blue);
                color: var(--light-cyan);
            }
            tr:nth-child(even) {
                background-color: var(--non-photo-blue);
            }
            tr:hover {
                background-color: var(--non-photo-blue-2);
            }
            .action-buttons a {
                margin: 0 5px;
                text-decoration: none;
                color: var(--honolulu-blue);
                transition: color 0.3s;
            }
            .action-buttons a:hover {
                color: var(--pacific-cyan);
            }
        </style>
    </head>
    <body>
        <div class="admin-wrapper">
            <h1>Category List</h1>
            <a href="<%= request.getContextPath() %>/categoryForm" class="action-link">Add New Category</a>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Category Name</th>
                    <th>Created At</th>
                    <th>Actions</th>
                </tr>
                <%
                  List<Category> categories = (List<Category>) request.getAttribute("categories");
                  if (categories != null) {
                    for (Category c : categories) {
                %>
                <tr>
                    <td><%= c.getcId() %></td>
                    <td><%= c.getcName() %></td>
                    <td><%= c.getCreatedAt() %></td>
                    <td class="action-buttons">
                        <a href="<%= request.getContextPath() %>/categoryForm?id=<%= c.getcId() %>">Edit</a> |
                        <a href="<%= request.getContextPath() %>/deleteCategory?id=<%= c.getcId() %>" onclick="return confirm('Are you sure?');">Delete</a>
                    </td>
                </tr>
                <%
                    }
                  }
                %>
            </table>
        </div>
    </body>
</html>
