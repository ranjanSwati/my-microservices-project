package com.ecom.orders.client;


import com.ecom.orders.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// name: Service ka naam, url: Product service ka address
@FeignClient(name = "products")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductDTO getProductById(@PathVariable("id") Long id);
}
