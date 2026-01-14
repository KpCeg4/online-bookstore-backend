package com.example.bookstore.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bookstore.Model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    Category findByName(String name);

}
