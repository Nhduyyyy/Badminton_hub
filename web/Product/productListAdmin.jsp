<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, entity.Product" %>
<%@ include file="/Common/navAdmin.jsp" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Admin - Danh sách Sản phẩm</title>
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
            body {
                margin: 0;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }
            /* Wrapper Admin */
            .admin-wrapper {
                background: linear-gradient(135deg, var(--non-photo-blue-2), var(--light-cyan));
                padding: 20px;
                min-height: 100vh;
            }
            .admin-wrapper h2 {
                color: var(--marian-blue);
                font-size: 2rem;
                text-align: center;
                margin-bottom: 20px;
            }
            .admin-wrapper h1 {
                color: var(--federal-blue);
                font-size: 1.8rem;
                text-align: center;
                margin-top: 30px;
                margin-bottom: 20px;
            }
            /* Form Tìm kiếm */
            form {
                text-align: center;
                margin-bottom: 20px;
            }
            form input[type="text"] {
                width: 300px;
                padding: 10px 15px;
                border: 2px solid var(--pacific-cyan);
                border-radius: 25px 0 0 25px;
                font-size: 1rem;
                outline: none;
                transition: border-color 0.3s;
            }
            form input[type="text"]:focus {
                border-color: var(--honolulu-blue);
            }
            form input[type="submit"] {
                padding: 10px 20px;
                border: none;
                border-radius: 0 25px 25px 0;
                background-color: var(--honolulu-blue);
                color: #fff;
                font-size: 1rem;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            form input[type="submit"]:hover {
                background-color: var(--pacific-cyan);
            }
            a {
                text-decoration: none;
                color: var(--honolulu-blue);
            }
            a:hover {
                color: var(--pacific-cyan);
            }
            /* Nút thêm sản phẩm */
            .create-product-btn {
                display: block;
                width: 180px;
                margin: 0 auto 30px;
                padding: 10px 20px;
                font-size: 1rem;
                border: 2px solid var(--honolulu-blue);
                color: var(--honolulu-blue);
                text-align: center;
                border-radius: 4px;
                transition: background-color 0.3s, color 0.3s;
            }
            .create-product-btn:hover {
                background-color: var(--honolulu-blue);
                color: #fff;
            }
            /* Bảng dữ liệu */
            table {
                width: 100%;
                border-collapse: collapse;
                background: #fff;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                overflow: hidden;
                margin-bottom: 20px;
            }
            table th, table td {
                padding: 10px;
                border: 1px solid #ddd;
                text-align: center;
                font-size: 0.9rem;
            }
            table th {
                background-color: var(--marian-blue);
                color: var(--light-cyan);
            }
            table tr:nth-child(even) {
                background-color: var(--non-photo-blue);
            }
            table tr:hover {
                background-color: var(--non-photo-blue-2);
            }
            /* Phân trang */
            .pagination {
                text-align: center;
                margin-top: 20px;
            }
            .pagination a, .pagination strong {
                display: inline-block;
                margin: 0 5px;
                padding: 8px 12px;
                border-radius: 4px;
                text-decoration: none;
                color: var(--federal-blue);
                border: 1px solid var(--non-photo-blue);
                transition: background-color 0.3s, color 0.3s;
            }
            .pagination a:hover {
                background-color: var(--pacific-cyan);
                color: #fff;
            }
            .pagination strong {
                background-color: var(--honolulu-blue);
                color: #fff;
                border-color: var(--honolulu-blue);
            }
        </style>
    </head>
    <body>
        <div class="admin-wrapper">
            <h2>Admin - Danh sách Sản phẩm</h2>

            <!-- Form tìm kiếm sản phẩm -->
            <form action="<%= request.getContextPath() %>/productlistadmin" method="get">
                <label>Từ khóa:</label>
                <input type="text" name="keyword" value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>" />
                <input type="submit" value="Search" />
                <!-- Nút Reset để hiển thị lại toàn bộ sản phẩm -->
                <a href="<%= request.getContextPath() %>/productlistadmin">Reset</a>
            </form>

            <a href="<%= request.getContextPath() %>/newProduct" class="create-product-btn">Thêm Sản phẩm Mới</a>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Tên sản phẩm</th>
                    <th>Mô tả</th>
                    <th>Giá</th>
                    <th>Tồn kho</th>
                    <th>Trạng thái</th>
                    <th>Thương hiệu</th>
                    <th>Danh mục</th>
                    <th>Hình ảnh</th>
                    <th>Ngày tạo</th>
                    <th>Hành động</th>
                </tr>
                <%
                  List<Product> listP = (List<Product>) request.getAttribute("listP");
                  if(listP != null) {
                    for(Product p : listP) {
                %>
                <tr>
                    <td><%= p.getProductId() %></td>
                    <td><%= p.getProductName() %></td>
                    <td><%= p.getDescription() %></td>
                    <td><%= p.getPrice() %></td>
                    <td><%= p.getStock() %></td>
                    <td><%= p.getStock() > 0 ? "Còn hàng" : "Hết hàng" %></td>
                    <td><%= p.getBrandId() %></td>
                    <td><%= p.getCategoryId() %></td>
                    <td><%= p.getImage() %></td>
                    <td><%= p.getCreatedAt() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/editProduct?id=<%= p.getProductId() %>">Sửa</a> |
                        <a href="<%= request.getContextPath() %>/deleteProduct?id=<%= p.getProductId() %>&categoryId=<%= p.getCategoryId() %>" onclick="return confirm('Bạn có chắc muốn xóa?');">Xóa</a>
                    </td>
                </tr>
                <%
                    }
                  } else {
                %>
                <tr>
                    <td colspan="11">Không có sản phẩm.</td>
                </tr>
                <%
                  }
                %>
            </table>

            <!-- Phân trang -->
            <div class="pagination">
                <%
                    int currentPage = (Integer) request.getAttribute("currentPage");
                    int totalPages = (Integer) request.getAttribute("totalPages");
                    String contextPath = request.getContextPath();
                    String baseUrl = contextPath + "/productlistadmin";
                    String keyword = request.getParameter("keyword");
                %>
                <div class="pagination">
                    <% for (int i = 1; i <= totalPages; i++) { 
       if (i == currentPage) { %>
                    <strong><%= i %></strong>
                    <%   } else { %>
                    <a href="<%= baseUrl %>?page=<%= i %><%= (keyword != null && !keyword.trim().isEmpty()) ? "&keyword=" + keyword : "" %>">
                        <%= i %>
                    </a>
                    <%   } 
                         out.print(" ");
                       } %>
                </div>

            </div>
        </div>
    </body>
</html>
