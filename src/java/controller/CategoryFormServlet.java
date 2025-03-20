package controller;

import entity.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.CategoryService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet(name = "CategoryFormServlet", urlPatterns = {"/categoryForm"})
public class CategoryFormServlet extends HttpServlet {
    private CategoryService categoryService;

    @Override
    public void init() {
        categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                Category category = categoryService.getCategoryById(id);
                request.setAttribute("category", category);
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }
        request.getRequestDispatcher("Category/categoryForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String categoryName = request.getParameter("categoryName");
        Category category = new Category();
        category.setcName(categoryName);
        try {
            if (idStr == null || idStr.isEmpty()) {
                // Thêm mới: đặt createdAt là thời gian hiện tại
                category.setCreatedAt(new Date());
                categoryService.addCategory(category);
            } else {
                category.setcId(Integer.parseInt(idStr));
                categoryService.updateCategory(category);
            }
            response.sendRedirect(request.getContextPath() + "/categoryList");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
