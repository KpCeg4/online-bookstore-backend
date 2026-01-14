package com.example.bookstore.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.bookstore.Model.PaymentRequest;
import com.example.bookstore.Service.PaymentService;
import com.razorpay.Order;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-order")
    public String createOrder(@RequestBody PaymentRequest request) throws Exception {
        Order order = paymentService.createOrder(request.getAmount());
        return order.toString();
    }

    @PostMapping("/verify")
    public boolean verifyPayment(@RequestBody Map<String, String> data) throws Exception {
        return paymentService.verifySignature(
                data.get("orderId"),
                data.get("paymentId"),
                data.get("signature")
        );
    }
}
