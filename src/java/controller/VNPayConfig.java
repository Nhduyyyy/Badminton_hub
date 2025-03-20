/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author admin
 */
public class VNPayConfig {
    public static final String vnp_TmnCode = "K9Y8CZOE"; 
    public static final String vnp_HashSecret = "ASDA73RBZDCTG0XGRGT1JDO3LDZ45V1J";

    // URL của cổng thanh toán sandbox
    public static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    // URL nhận phản hồi sau thanh toán (cần đúng với servlet của bạn)
    public static final String vnp_ReturnUrl = "http://localhost:8080/badminton/vnpayreturn";
}
