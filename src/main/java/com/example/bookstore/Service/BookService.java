package com.example.bookstore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Category;
import com.example.bookstore.Repo.BookRepo;
import com.example.bookstore.Repo.CategoryRepo;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public Book save(Book book) {
        if (book.getStock() == null || book.getStock() < 0) {
            book.setStock(0);
        }
        return bookRepo.save(book);
    }

    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    public Book getById(Long id) {
        return bookRepo.findById(id).orElse(null);
    }

    public Book update(Book book) {
        if (book.getId() == null || !bookRepo.existsById(book.getId())) {
            throw new RuntimeException("Book not found");
        }

        if (book.getStock() == null || book.getStock() < 0) {
            throw new RuntimeException("Invalid stock value");
        }

        return bookRepo.save(book);
    }

    public Book updateStock(Long bookId, Integer stock) {
        if (stock < 0) {
            throw new RuntimeException("Stock cannot be negative");
        }

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setStock(stock);
        return bookRepo.save(book);
    }

    public Book updateCategory(Long bookId, Long categoryId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        book.setCategory(category);
        return bookRepo.save(book);
    }

    public void delete(Long id) {
        if (!bookRepo.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        bookRepo.deleteById(id);
    }
}
