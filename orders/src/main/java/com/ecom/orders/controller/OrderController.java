package com.ecom.orders.controller;


import com.ecom.orders.client.ProductClient;
import com.ecom.orders.dto.ProductDTO;
import com.ecom.orders.entity.Order;
import com.ecom.orders.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
@RefreshScope
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderRepository orderRepository;


    @Value("${example.property: Hello}")
    private String messageFromGithub;

    @GetMapping("/test-config")
    public String getMessage() {
        return "Config Server says: " + messageFromGithub;
    }

    // --- MAIN METHOD ---
    @PostMapping
    @CircuitBreaker(name = "productBreaker", fallbackMethod = "productDownFallback")
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {

        // Step 1: Product Service ko call karke details mangwayin
        ProductDTO product = productClient.getProductById(order.getProductId());

        if (product != null) {
            // Step 2: Price calculation aur data set karna
            order.setTotalPrice(product.getPrice() * order.getQuantity());
            order.setOrderDate(LocalDateTime.now());

            // Step 3: Order save karna
            Order savedOrder = orderRepository.save(order);

            // SUCCESS: Order object return kar rahe hain (200 OK)
            return ResponseEntity.ok(savedOrder);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product ID galat hai, product nahi mila.");
        }
    }

    // --- FALLBACK METHOD (Plan B) ---
    // Rule: Arguments bilkul same hone chahiye (Order order) + (Throwable t)
    public ResponseEntity<?> productDownFallback(Order order, Throwable t) {

        // Console mein error print karein (Debugging ke liye)
        System.out.println("Fallback executed due to: " + t.getMessage());

        // FAILURE: String message return kar rahe hain (503 Service Unavailable)
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("⚠️ Product Service abhi Down hai. Kripya thodi der baad try karein.");
    }
}



