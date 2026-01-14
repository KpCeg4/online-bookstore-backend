package com.example.bookstore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Model.Category;
import com.example.bookstore.Repo.BookRepo;
import com.example.bookstore.Repo.CategoryRepo;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private BookRepo bookRepo;

    public Category save(Category category) {
        if (categoryRepo.existsByName(category.getName())) {
            throw new RuntimeException("Category already exists");
        }
        return categoryRepo.save(category);
    }

    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    public Category getById(Long id) {
        return categoryRepo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!bookRepo.findByCategory(category).isEmpty()) {
            throw new RuntimeException("Cannot delete category with existing books");
        }

        categoryRepo.delete(category);
    }
}
