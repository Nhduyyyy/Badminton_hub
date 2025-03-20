/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author admin
 */
public class Cart {

    private Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void addItem(Product product, int quantity) {
        if (quantity < 1) {
            return;
        }
        int stock = product.getStock();
        int productId = product.getProductId();
        if (items.containsKey(productId)) {
            CartItem item = items.get(productId);
            int newQuantity = item.getQuantity() + quantity;
            if (newQuantity > stock) {
                System.out.println("Vượt quá số lượng tồn kho!");
                return;
            }
            item.setQuantity(newQuantity);
        } else {
            if (quantity > stock) {
                System.out.println("Vượt quá số lượng tồn kho!");
                return;
            }
            items.put(productId, new CartItem(product, quantity));
        }

    }

    public void removeItem(int productId) {
        items.remove(productId);
    }

    public void updateQuantity(int productId, int quantity) {
        if (quantity < 1) {
            removeItem(productId);
            System.out.println("Đã xoa pham co id :  " + productId);
            return;
        }

        if (items.containsKey(productId)) {
            CartItem item = items.get(productId);
            int stock = item.getProduct().getStock();
            if (quantity > stock) {
                System.out.println("Số lượng cập nhật vượt quá tồn kho!");
                return;
            }
            item.setQuantity(quantity);
            System.out.println("Cập nhật sản phẩm ID: " + productId + " số lượng: " + quantity);
        }
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getTotal();
        }
        return total;
    }

    public int getTotalProductQuantity() {
        int total = 0;
        for (CartItem item : items.values()) {
            total += item.getQuantity();
        }
        return total;
    }

    public int getTotalProductCount() {
        return items.keySet().size(); // Đếm số loại sản phẩm khác nhau
    }
}
