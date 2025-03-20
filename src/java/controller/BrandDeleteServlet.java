package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.BrandService;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BrandDeleteServlet", urlPatterns = {"/deleteBrand"})
public class BrandDeleteServlet extends HttpServlet {

    private BrandService brandService;

    @Override
    public void init() {
        brandService = new BrandService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                brandService.deleteBrand(id);
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }
        response.sendRedirect(request.getContextPath() + "/brandList");
    }
}
