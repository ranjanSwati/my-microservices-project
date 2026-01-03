package com.ecom.products.repo;



import com.ecom.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Basic CRUD methods JpaRepository se mil jayenge
}
