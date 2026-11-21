package com.retailops.product.controller;

import com.retailops.product.entity.Product;
import com.retailops.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/{id}")
    public Product updateProduct(
            @PathVariable("id") Long id,
            @RequestBody Product product) {

        return productService.updateProduct(id, product);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteProductById(@PathVariable("id") Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }


}
