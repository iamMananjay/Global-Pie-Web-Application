package com.example.globalpie.controller;

import com.example.globalpie.model.Category;
import com.example.globalpie.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category createCategory(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam MultipartFile image
    ) throws IOException {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description); // Set the description
        category.setImage(image.getBytes());
        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam MultipartFile image
    ) throws IOException {
        return categoryRepository.findById(id).map(category -> {
            category.setName(name);
            category.setDescription(description); // Update the description
            try {
                category.setImage(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok(categoryRepository.save(category));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        return categoryRepository.findById(id).map(category -> {
            categoryRepository.delete(category);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
