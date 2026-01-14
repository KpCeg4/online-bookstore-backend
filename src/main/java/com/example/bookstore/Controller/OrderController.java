package com.example.bookstore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.bookstore.Model.Order;
import com.example.bookstore.Service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order placeOrder(
            @RequestBody Order order,
            Authentication authentication
    ) {
        String email = authentication.getName();
        order.setUserEmail(email);
        return orderService.placeOrder(order);
    }

    @GetMapping("/user")
    public List<Order> getUserOrders(Authentication authentication) {
        return orderService.getOrdersByUser(authentication.getName());
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
