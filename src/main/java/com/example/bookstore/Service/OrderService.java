package com.example.bookstore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Order;
import com.example.bookstore.Repo.BookRepo;
import com.example.bookstore.Repo.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private BookRepo bookRepo;

    public Order placeOrder(Order order) {
        order.getItems().forEach(item -> {
            Book book = bookRepo.findById(item.getBookId()).orElseThrow();
            book.setStock(book.getStock() - item.getQuantity());
            bookRepo.save(book);
        });

        return orderRepo.save(order);
    }

    public List<Order> getOrdersByUser(String email) {
        return orderRepo.findByUserEmail(email);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
