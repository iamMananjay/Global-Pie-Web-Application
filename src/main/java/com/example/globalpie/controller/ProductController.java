package com.example.globalpie.controller;
import com.example.globalpie.model.SubCategory;

import com.example.globalpie.model.Product;
import com.example.globalpie.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


@PostMapping
public Product createProduct(
        @RequestParam String name,
        @RequestParam String specification,
        @RequestParam Long subCategoryId,
        @RequestParam MultipartFile image
) throws IOException {
    Product product = new Product();
    product.setName(name);
    product.setSpecification(specification);
    product.setSubCategory(new SubCategory());
    product.getSubCategory().setId(subCategoryId);

    // Save the image as a byte array in the database
    product.setImage(image.getBytes());

    return productRepository.save(product);
}


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestParam String name, @RequestParam String specification, @RequestParam Long subCategoryId) {
        return productRepository.findById(id).map(product -> {
            product.setName(name);
            product.setSpecification(specification);
            product.setSubCategory(new SubCategory());
            product.getSubCategory().setId(subCategoryId);
            return ResponseEntity.ok(productRepository.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }
    // Get a single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

