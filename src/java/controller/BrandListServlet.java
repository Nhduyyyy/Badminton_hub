package controller;

import entity.Brand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.BrandService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "BrandListServlet", urlPatterns = {"/brandList"})
public class BrandListServlet extends HttpServlet {

    private BrandService brandService;

    @Override
    public void init() {
        brandService = new BrandService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Brand> brands = brandService.getAllBrands();
            request.setAttribute("brands", brands);
            request.getRequestDispatcher("Brand/brandList.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
