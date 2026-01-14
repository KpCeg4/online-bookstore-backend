package com.example.bookstore.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.Model.User;

public interface UserRepo extends JpaRepository<User,Long> {
	User findByEmail(String email);
}
