package com.example.bookstore.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Category;

public interface BookRepo extends JpaRepository<Book, Long> {

    List<Book> findByCategory(Category category);

}
