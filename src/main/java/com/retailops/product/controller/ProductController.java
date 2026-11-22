package com.retailops.product.controller;

import com.retailops.product.dto.CreateProductRequest;
import com.retailops.product.dto.ProductResponse;
import com.retailops.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse createProduct(
            @RequestBody CreateProductRequest request) {

        return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable("id") Long id,
            @RequestBody CreateProductRequest request) {

        return productService.updateProduct(id, request);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteProductById(@PathVariable("id") Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }


}
