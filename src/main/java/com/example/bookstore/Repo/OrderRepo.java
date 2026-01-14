package com.example.bookstore.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bookstore.Model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByUserEmail(String userEmail);
}
