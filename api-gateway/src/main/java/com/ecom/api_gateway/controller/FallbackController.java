package com.ecom.api_gateway.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/order")
    public Mono<String> orderServiceFallback() {
        // Mono isliye kyunki Gateway Reactive (Non-blocking) hai
        return Mono.just("🚦 Gateway Alert: Order Service abhi respond nahi kar raha. Kripya baad mein try karein.");
    }

    @RequestMapping("/fallback/product")
    public Mono<String> productServiceFallback() {
        return Mono.just("🚦 Gateway Alert: Product Service down hai.");
    }
}
