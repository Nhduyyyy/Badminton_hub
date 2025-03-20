<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.Product, entity.Brand, entity.Category, java.util.List" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Form Sản phẩm</title>
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
                max-width: 600px;
                margin: 40px auto;
                background: #fff;
                padding: 30px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                border-radius: 8px;
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
            form input[type="text"],
            form textarea,
            form select {
                width: 100%;
                padding: 8px;
                border: 1px solid var(--non-photo-blue);
                border-radius: 4px;
                margin-bottom: 15px;
                font-size: 1rem;
            }
            form textarea {
                resize: vertical;
                height: 100px;
            }
            form input[type="submit"] {
                display: block;
                width: 100%;
                padding: 10px;
                background-color: var(--honolulu-blue);
                border: none;
                border-radius: 4px;
                color: #fff;
                font-size: 1.1rem;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            form input[type="submit"]:hover {
                background-color: var(--pacific-cyan);
            }
            a.back-link {
                display: block;
                text-align: center;
                margin-top: 20px;
                color: var(--honolulu-blue);
                text-decoration: none;
            }
            a.back-link:hover {
                color: var(--pacific-cyan);
            }
        </style>
    </head>
    <body>
        <div class="form-wrapper">
            <h1>
                <% if(request.getAttribute("product") != null) { %>
                Chỉnh sửa Sản phẩm
                <% } else { %>
                Thêm Sản phẩm Mới
                <% } %>
            </h1>
            <form action="<%= request.getAttribute("product") != null ? "updateProduct" : "insertProduct" %>" method="post">
                <% 
                  Product product = (Product) request.getAttribute("product");
                  if(product != null) { 
                %>
                <input type="hidden" name="id" value="<%= product.getProductId() %>" />
                <% } %>

                <label for="productName">Tên sản phẩm:</label>
                <input type="text" id="productName" name="productName" value="<%= product != null ? product.getProductName() : "" %>" required />

                <label for="description">Mô tả:</label>
                <textarea id="description" name="description" required><%= product != null ? product.getDescription() : "" %></textarea>

                <label for="price">Giá:</label>
                <input type="text" id="price" name="price" value="<%= product != null ? product.getPrice() : "" %>" required />

                <label for="stock">Tồn kho:</label>
                <input type="text" id="stock" name="stock" value="<%= product != null ? product.getStock() : "" %>" required />

                <label for="status">Trạng thái:</label>
                <select id="status" name="status" required>
                    <option value="Còn hàng" <%= (product == null || "Còn hàng".equals(product.getStatus())) ? "selected" : "" %>>Còn hàng</option>
                    <option value="Hết hàng" <%= (product != null && "Hết hàng".equals(product.getStatus())) ? "selected" : "" %>>Hết hàng</option>
                </select>

                <label for="brandId">Thương hiệu:</label>
                <select id="brandId" name="brandId" required>
                    <%
                      List<Brand> listB = (List<Brand>) request.getAttribute("listB");
                      if(listB != null) {
                          for(Brand b : listB) {
                    %>
                    <option value="<%= b.getBrandId() %>" <%= (product != null && product.getBrandId() == b.getBrandId()) ? "selected" : "" %>>
                        <%= b.getBrandName() %>
                    </option>
                    <%
                          }
                      }
                    %>
                </select>

                <label for="categoryId">Danh mục:</label>
                <select id="categoryId" name="categoryId" required>
                    <%
                      List<Category> listC = (List<Category>) request.getAttribute("listC");
                      if(listC != null) {
                          for(Category c : listC) {
                    %>
                    <option value="<%= c.getcId() %>" <%= (product != null && product.getCategoryId() == c.getcId()) ? "selected" : "" %>>
                        <%= c.getcName() %>
                    </option>
                    <%
                          }
                      }
                    %>
                </select>

                <label for="image">Hình ảnh (đường dẫn):</label>
                <input type="text" id="image" name="image" value="<%= product != null ? product.getImage() : "" %>" required />

                <input type="submit" value="Lưu" />
            </form>
            <a href="${pageContext.request.contextPath}/productlistadmin" class="back-link">Quay lại danh sách sản phẩm</a>
        </div>
    </body>
</html>
