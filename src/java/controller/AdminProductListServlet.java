package controller;

import entity.Product;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ProductService;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminProductListServlet", urlPatterns = {"/productlistadmin"})
public class AdminProductListServlet extends HttpServlet {

    // Số sản phẩm mỗi trang
    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            ProductService productService = new ProductService();

            // Lấy tham số tìm kiếm từ form JSP
            String keyword = request.getParameter("keyword");
            List<Product> listP;
            int totalProducts;
            int currentPage = 1;

            // Lấy tham số trang hiện tại, mặc định là 1
            String pageStr = request.getParameter("page");
            if (pageStr != null && !pageStr.trim().isEmpty()) {
                try {
                    currentPage = Integer.parseInt(pageStr);
                } catch (NumberFormatException ex) {
                    currentPage = 1;
                }
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                // Nếu có keyword, sử dụng hàm tìm kiếm
                listP = productService.searchProducts(keyword);
                totalProducts = listP.size();

                // Phân trang thủ công cho kết quả tìm kiếm
                int start = (currentPage - 1) * PAGE_SIZE;
                int end = Math.min(start + PAGE_SIZE, totalProducts);
                if (start >= totalProducts) {  // nếu trang vượt quá số lượng sản phẩm
                    start = 0;
                    currentPage = 1;
                    end = Math.min(PAGE_SIZE, totalProducts);
                }
                listP = listP.subList(start, end);
                int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("keyword", keyword);
            } else {
                // Nếu không có keyword, sử dụng phân trang thông thường
                int offset = (currentPage - 1) * PAGE_SIZE;
                listP = productService.getProductsPaginated(offset, PAGE_SIZE);
                totalProducts = productService.getProductsCount();
                int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("totalPages", totalPages);
            }

            request.setAttribute("listP", listP);
            request.getRequestDispatcher("Product/productListAdmin.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
