package com.example.bookstore.Config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Category;
import com.example.bookstore.Repo.BookRepo;
import com.example.bookstore.Repo.CategoryRepo;

@Component
public class CsvDataLoader implements CommandLineRunner {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private BookRepo bookRepo;

    @Override
    public void run(String... args) throws Exception {

       if (categoryRepo.count() > 0 || bookRepo.count() > 0) {
            return;
        } 

        Map<String, Category> categoryMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("data/categories.csv").getInputStream()
                ))) {

            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                Category category = new Category();
                category.setName(line.trim());
                categoryRepo.save(category);
                categoryMap.put(category.getName(), category);
            }
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("data/books.csv").getInputStream()
                ))) {

            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (d.length < 10) continue;

                Book book = new Book();
                book.setTitle(strip(d[0]));
                book.setAuthor(strip(d[1]));
                book.setIsbn(strip(d[2]));
                book.setLanguage(strip(d[3]));
                book.setPublisher(strip(d[4]));
                book.setPrice(Double.parseDouble(d[5]));
                book.setStock(Integer.parseInt(d[6]));
                book.setDescription(strip(d[7]));
                book.setImageUrl(strip(d[8]));
                book.setCategory(categoryMap.get(strip(d[9])));

                bookRepo.save(book);
            }
        }
    }

    private String strip(String value) {
        return value.replaceAll("^\"|\"$", "").trim();
    }
}
