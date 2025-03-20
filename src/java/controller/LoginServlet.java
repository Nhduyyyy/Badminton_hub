/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import UserDAO.UserDAO;
import entity.User;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.loginService;


@WebServlet(name = "loginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private loginService loginService = new loginService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        request.getRequestDispatcher("/authenticator_authorization/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String identifier = request.getParameter("identifier");
        String password = request.getParameter("password");
        String remPass = request.getParameter("rem");

        Cookie cu = new Cookie("cuser", identifier);
        Cookie cp = new Cookie("cpass", password);
        Cookie cr = new Cookie("crem", remPass);

        if (remPass != null) {
            cu.setMaxAge(60 * 60 * 24 * 7);
            cp.setMaxAge(60 * 60 * 24 * 7);
            cr.setMaxAge(60 * 60 * 24 * 7);
        } else {
            cu.setMaxAge(0);
            cp.setMaxAge(0);
            cr.setMaxAge(0);
        }

        response.addCookie(cu);
        response.addCookie(cp);
        response.addCookie(cr);
       
        User user = loginService.authenticate(identifier, password);
        
        if (user != null) {

            if (user.isLocked()) {
                request.setAttribute("errorMessage", "Tài khoản của bạn đang bị khóa.");
                request.getRequestDispatcher("login").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            
            session.removeAttribute("cart");
            
            session.setAttribute("user", user);
            
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng.");
            request.getRequestDispatcher("login").forward(request, response);
        }
    }
    
}