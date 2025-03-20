package controller;

import entity.Brand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.BrandService;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BrandFormServlet", urlPatterns = {"/brandForm"})
public class BrandFormServlet extends HttpServlet {

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
                Brand brand = brandService.getBrandById(id);
                request.setAttribute("brand", brand);
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }
        request.getRequestDispatcher("Brand/brandForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String brandName = request.getParameter("brandName");
        Brand brand = new Brand();
        brand.setBrandName(brandName);
        try {
            if (idStr == null || idStr.isEmpty()) {
                // Thêm mới
                brandService.addBrand(brand);
            } else {
                // Cập nhật
                brand.setBrandId(Integer.parseInt(idStr));
                brandService.updateBrand(brand);
            }
            response.sendRedirect(request.getContextPath() + "/brandList");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
