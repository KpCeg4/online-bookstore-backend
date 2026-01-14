package com.example.bookstore.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

@Service
public class PaymentService {

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    public Order createOrder(Double amount) throws Exception {
        JSONObject options = new JSONObject();
        options.put("amount", amount.intValue() * 100);
        options.put("currency", "INR");
        options.put("payment_capture", 1);

        return razorpayClient.orders.create(options);
    }

    public boolean verifySignature(String orderId, String paymentId, String signature) throws Exception {
        JSONObject payload = new JSONObject();
        payload.put("razorpay_order_id", orderId);
        payload.put("razorpay_payment_id", paymentId);
        payload.put("razorpay_signature", signature);

        return Utils.verifyPaymentSignature(payload, keySecret);
    }
}
