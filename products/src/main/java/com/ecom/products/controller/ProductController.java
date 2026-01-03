package com.ecom.products.controller;


import com.ecom.products.entity.Product;
import com.ecom.products.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Environment env; // Port number janne ke liye

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        // Console mein print karega ki request kis port par aayi
        String port = env.getProperty("local.server.port");
        System.out.println("📢 Request Handled by Product Service on PORT: " + port);

        return productRepository.findById(id).orElse(null);
    }

    // 1. Product Create karne ke liye
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // 2. Sare Products lane ke liye
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }



}
