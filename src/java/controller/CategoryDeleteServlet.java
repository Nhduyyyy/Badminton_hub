package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.CategoryService;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CategoryDeleteServlet", urlPatterns = {"/deleteCategory"})
public class CategoryDeleteServlet extends HttpServlet {
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
                categoryService.deleteCategory(id);
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }
        response.sendRedirect(request.getContextPath() + "/categoryList");
    }
}
