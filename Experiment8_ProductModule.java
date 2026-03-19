package com.example.product;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.*;
import java.util.*;

// -------------------- ENTITY --------------------
@Entity
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String category;
    private double price;

    public Product() {}

    public Product(int id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}

// -------------------- REPOSITORY --------------------
interface ProductRepository extends JpaRepository<Product, Integer> {

    // Derived Query Methods
    List<Product> findByCategory(String category);

    List<Product> findByPriceBetween(double min, double max);

    // JPQL Queries

    // a. Sort by price
    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    List<Product> getAllSortedByPrice();

    // b. Products above price
    @Query("SELECT p FROM Product p WHERE p.price > :price")
    List<Product> getExpensiveProducts(@Param("price") double price);

    // c. Products by category (JPQL)
    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> getProductsByCategoryJPQL(@Param("category") String category);
}

// -------------------- CONTROLLER --------------------
@RestController
@RequestMapping("/products")
class ProductController {

    @Autowired
    private ProductRepository repo;

    // a. Category search
    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable String category) {
        return repo.findByCategory(category);
    }

    // b. Price filter
    @GetMapping("/filter")
    public List<Product> filterByPrice(@RequestParam double min,
                                       @RequestParam double max) {
        return repo.findByPriceBetween(min, max);
    }

    // c. Sorted products
    @GetMapping("/sorted")
    public List<Product> getSortedProducts() {
        return repo.getAllSortedByPrice();
    }

    // d. Expensive products
    @GetMapping("/expensive/{price}")
    public List<Product> getExpensive(@PathVariable double price) {
        return repo.getExpensiveProducts(price);
    }
}
