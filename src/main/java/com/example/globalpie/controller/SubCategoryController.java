package com.example.globalpie.controller;

import com.example.globalpie.model.SubCategory;
import com.example.globalpie.repository.SubCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.globalpie.model.Category;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {
    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryController(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @GetMapping
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    @PostMapping
    public SubCategory createSubCategory(@RequestParam String name, @RequestParam MultipartFile image, @RequestParam Long categoryId) throws IOException {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(name);
        subCategory.setImage(image.getBytes());
        subCategory.setCategory(new Category());
        subCategory.getCategory().setId(categoryId);
        return subCategoryRepository.save(subCategory);
    }

    @GetMapping("/{name}/subcategories")
    public ResponseEntity<List<SubCategory>> getSubcategoriesByCategoryName(@PathVariable String name) {
        // Replace hyphens with spaces to match the category name format in the database
        String categoryName = name.replace("-", " "); // Convert hyphens to spaces
        List<SubCategory> subcategories = subCategoryRepository.findByCategoryName(categoryName);
        if (subcategories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subcategories);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable Long id, @RequestParam String name, @RequestParam MultipartFile image, @RequestParam Long categoryId) throws IOException {
        return subCategoryRepository.findById(id).map(subCategory -> {
            subCategory.setName(name);
            try {
                subCategory.setImage(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            subCategory.setCategory(new Category());
            subCategory.getCategory().setId(categoryId);
            return ResponseEntity.ok(subCategoryRepository.save(subCategory));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubCategory(@PathVariable Long id) {
        return subCategoryRepository.findById(id).map(subCategory -> {
            subCategoryRepository.delete(subCategory);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
