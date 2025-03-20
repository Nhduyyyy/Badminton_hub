package controller;

import entity.Cart;
import entity.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.ProductService;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private ProductService productService = new ProductService();
    private Product product = new Product();
    private Cart cart;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Cart/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        switch (action) {
            case "add":
                addToCart(request, response);
                break;
            case "update":
                updateCart(request, response);
                break;
            case "remove":
                removeFromCart(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

    public void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        product = productService.selectProduct(productId);
        HttpSession session = request.getSession();
        cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        if (product != null) {
            if (quantity > product.getStock()) {
                request.setAttribute("product", product);
                request.getRequestDispatcher("/Product/productDetail.jsp").forward(request, response);
                return;
            } else if (quantity > product.getStock()) {
                request.setAttribute("product", product);
                request.getRequestDispatcher("/Product/productDetail.jsp").forward(request, response);
                return;
            } else {
                cart.addItem(product, quantity);
                session.setAttribute("cart", cart);
            }
        }
        response.sendRedirect("/badminton/Cart/cart.jsp");
    }

    public void updateCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
int quantity = Integer.parseInt(request.getParameter("quantity"));

        HttpSession session = request.getSession();
        cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            product = productService.selectProduct(productId);

            if (product != null) {
                int stock = product.getStock();

                if (quantity > stock) {
                    session.setAttribute("updateCartError", "Vượt quá số lượng tồn kho!");
                    response.sendRedirect("/badminton/Cart/cart.jsp");
                    return;
                } else {
                    if (quantity > 0) {
                        cart.updateQuantity(productId, quantity);
                    } else {
                        cart.removeItem(productId);
                    }
                    session.setAttribute("cart", cart);
                }
            }
        }

        response.sendRedirect("/badminton/Cart/cart.jsp");
    }

    public void removeFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        HttpSession session = request.getSession();
        cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.removeItem(productId);
        }
        session.setAttribute("cart", cart);
        response.sendRedirect("/badminton/Cart/cart.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}