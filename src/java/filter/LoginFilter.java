package filter;

import entity.Role;
import entity.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(filterName = "LoginFilter", urlPatterns = {"/*"})
public class LoginFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        // Loại trừ các trang/tài nguyên không cần kiểm tra
        // - endsWith("login.jsp") để bỏ qua trang login
        // - endsWith("403.jsp") để tránh vòng lặp khi redirect 403
        // - endsWith(".css"), endsWith(".js"), v.v. nếu bạn có file tĩnh
        // Lấy tham số action từ request
        String actionParam = req.getParameter("action");
        if (uri.endsWith("login.jsp")
                || uri.contains("/home")
                || uri.endsWith("/home.jsp")
                || uri.endsWith("/login")
                || uri.endsWith("/register.jsp")
                || uri.endsWith("/register")
                || uri.endsWith("/logout")
                || uri.endsWith("/chat.jsp")
                || uri.contains("/ChatServlet")
                || uri.contains("/LoginGoogleHandler")
                || uri.contains("/FetchMessagesServlet")
                || uri.contains("/TelegramWebhookServlet")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // Chưa đăng nhập => chuyển hướng login
            res.sendRedirect(req.getContextPath() + "/authenticator_authorization/login.jsp");
            return;
        }

        // Đã đăng nhập => Lấy role
        User a = (User) session.getAttribute("user");
        Role role = a.getRole();

        // Kiểm tra quyền
        if (role.getRoleId() == 1) {
            // Admin => được phép tất cả (list, create, update, delete)
            chain.doFilter(request, response);
        } else if (role.getRoleId() == 2) {
            // Manager => được crud với product
            if (uri.contains("/productlistadmin")          || uri.endsWith("productListAdmin.jsp")
                    || uri.contains("/insertProduct")      || uri.endsWith("productForm.jsp") 
                    || uri.contains("/updateProduct")      || uri.contains("/newProduct")
                    || uri.contains("/editProduct")        || uri.contains("/deleteProduct") 
                    
                    || uri.contains("/deleteBrand")  
                    || uri.contains("/brandForm")          || uri.endsWith("brandForm.jsp")
                    || uri.contains("/brandList")          || uri.endsWith("brandList.jsp")
                    
                    || uri.contains("/deleteCategory") 
                    || uri.contains("/categoryForm")       || uri.endsWith("categoryForm.jsp")
                    || uri.contains("/categoryList")       || uri.endsWith("categoryList.jsp")
                    
                    // User dùng được
                    || uri.contains("/cart")               || uri.endsWith("cart.jsp")
                    || uri.contains("/category")           || uri.contains("/productListServlet") 
                    || uri.contains("/checkout")           || uri.endsWith("orderSuccess.jsp")                    
                     
                    || uri.contains("/productdetail")      || uri.endsWith("productDetail.jsp")
                    || uri.contains("/forgot-password")    || uri.endsWith("forgotPassword.jsp")
                    
                    || uri.contains("/orderhistory")       || uri.endsWith("orderHistory.jsp")
                    || uri.contains("/paymentconfirm")     || uri.endsWith("forgotPassword.jsp")
                    
                    || uri.contains("/productlist")        || uri.endsWith("productList.jsp")
                    || uri.contains("/profile")            || uri.endsWith("profile.jsp")
                    || uri.contains("/review")             || uri.contains("/searchA")
                    || uri.contains("/vnpayreturn")        || uri.contains("/we")) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/authenticator_authorization/login.jsp");
            }
        } else {
            if (       uri.contains("/cart")               || uri.endsWith("cart.jsp")
                    || uri.contains("/category")           || uri.contains("/productListServlet") 
                    || uri.contains("/checkout")           || uri.endsWith("orderSuccess.jsp")                    
                     
                    || uri.contains("/productdetail")      || uri.endsWith("productDetail.jsp")
                    || uri.contains("/forgot-password")    || uri.endsWith("forgotPassword.jsp")
                    
                    || uri.contains("/orderhistory")       || uri.endsWith("orderHistory.jsp")
                    || uri.contains("/paymentconfirm")     || uri.endsWith("forgotPassword.jsp")
                    
                    || uri.contains("/productlist")        || uri.endsWith("productList.jsp")
                    || uri.contains("/profile")            || uri.endsWith("profile.jsp")
                    || uri.contains("/review")             || uri.contains("/searchA")
                    || uri.contains("/vnpayreturn")        || uri.contains("/we")) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/authenticator_authorization/login.jsp");
            }
        }
    }
}
