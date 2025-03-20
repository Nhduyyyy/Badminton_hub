
package controller;


import entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import services.ListUserService;

@WebServlet(name = "userManagementServlet", urlPatterns = {"/userManagement"})
public class UserManagementServlet extends HttpServlet {

    private ListUserService userService = new ListUserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số tìm kiếm (query) và bộ lọc (roleFilter, statusFilter)
        String query = request.getParameter("query");
        String roleFilter = request.getParameter("roleFilter");
        String statusFilter = request.getParameter("statusFilter");

        if (roleFilter == null || roleFilter.trim().isEmpty()) {
            roleFilter = "all";
        }
        if (statusFilter == null || statusFilter.trim().isEmpty()) {
            statusFilter = "all";
        }

        // Lấy số bản ghi trên mỗi trang, mặc định là 5
        int pageSize = 5;
        String pageSizeParam = request.getParameter("pageSize");
        if (pageSizeParam != null) {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
            } catch (NumberFormatException e) {
                pageSize = 5;
            }
        }

        // Lấy trang hiện tại, mặc định là trang 1
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        int offset = (page - 1) * pageSize;

        // Lấy thông tin user đang đăng nhập từ session
        User currentUser = (User) request.getSession().getAttribute("user");
        Integer excludeUserId = null;
        if (currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole().getRoleName())) {
            excludeUserId = currentUser.getUserId();
        }

        // Sử dụng service để lấy danh sách người dùng và đếm tổng số
        List<User> userList = userService.getUsers(query, roleFilter, statusFilter, offset, pageSize, excludeUserId);
        int totalUsers = userService.getTotalUsers(query, roleFilter, statusFilter, excludeUserId);
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        // Đẩy dữ liệu lên request để hiển thị trên JSP
        request.setAttribute("userList", userList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("roleFilter", roleFilter);
        request.setAttribute("statusFilter", statusFilter);

        request.getRequestDispatcher("/user/adminManageUsers.jsp").forward(request, response);
    }
}

