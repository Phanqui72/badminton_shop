package com.mgr.api.controller;

import com.mgr.api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create-payment")
    public void createPayment(HttpServletRequest request, HttpServletResponse response)
            throws IOException, UnsupportedEncodingException { // Thêm 2 Exception này vào đây

        long amount = 1000000; // Số tiền: 10,000 VND
        String paymentUrl = paymentService.createPayment(request, amount);

        // Chuyển hướng trình duyệt sang VNPAY
        response.sendRedirect(paymentUrl);
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentReturn(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if ("00".equals(status)) {
            return "Thanh toán thành công!";
        } else {
            return "Thanh toán thất bại hoặc đã hủy giao dịch.";
        }
    }
}