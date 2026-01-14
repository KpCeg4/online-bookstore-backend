package com.example.bookstore.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Category;
import com.example.bookstore.Service.BookService;
import com.example.bookstore.Repo.CategoryRepo;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryRepo categoryRepo;

    @PostMapping
    public Book save(@RequestBody Map<String, Object> data) {

        Category category = null;

        if (data.containsKey("categoryId")) {
            Long categoryId = Long.valueOf(data.get("categoryId").toString());
            category = categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        } 
        else if (data.containsKey("category")) {
            Object obj = data.get("category");
            if (obj instanceof Map<?, ?> map && map.get("id") != null) {
                Long id = Long.valueOf(map.get("id").toString());
                category = categoryRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Category not found"));
            }
        } 
        else if (data.containsKey("categoryName")) {
            String name = data.get("categoryName").toString();
            category = categoryRepo.findByName(name);
            if (category == null) {
                throw new RuntimeException("Category not found: " + name);
            }
        }

        if (category == null) {
            throw new RuntimeException("Category is required");
        }

        Book book = new Book();
        book.setTitle((String) data.get("title"));
        book.setAuthor((String) data.get("author"));
        book.setIsbn((String) data.get("isbn"));
        book.setLanguage((String) data.get("language"));
        book.setPublisher((String) data.get("publisher"));
        book.setPrice(Double.valueOf(data.get("price").toString()));
        book.setStock(Integer.valueOf(data.get("stock").toString()));
        book.setDescription((String) data.get("description"));
        book.setImageUrl((String) data.get("imageUrl"));
        book.setCategory(category);

        return bookService.save(book);
    }

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PutMapping
    public Book update(@RequestBody Book book) {
        return bookService.update(book);
    }

    @PutMapping("/{id}/stock")
    public Book updateStock(@PathVariable Long id, @RequestParam Integer stock) {
        return bookService.updateStock(id, stock);
    }

    @PutMapping("/{id}/category/{categoryId}")
    public Book updateCategory(
            @PathVariable Long id,
            @PathVariable Long categoryId) {
        return bookService.updateCategory(id, categoryId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
