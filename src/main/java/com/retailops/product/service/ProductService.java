package com.retailops.product.service;


import com.retailops.common.exception.DuplicateSkuException;
import com.retailops.common.exception.ProductNotFoundException;
import com.retailops.product.dto.CreateProductRequest;
import com.retailops.product.dto.ProductResponse;
import com.retailops.product.entity.Product;
import com.retailops.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(CreateProductRequest request) {


        if (productRepository.existsBySku(request.getSku())) {
            throw new DuplicateSkuException(
                    "Product with SKU " + request.getSku() + " already exists"
            );
        }

        Product product = new Product();

        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        Product savedProduct = productRepository.save(product);

        ProductResponse response = new ProductResponse();

        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setSku(savedProduct.getSku());
        response.setPrice(savedProduct.getPrice());
        response.setQuantity(savedProduct.getQuantity());

        return response;
    }

    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(product -> {
                    ProductResponse response = new ProductResponse();

                    response.setId(product.getId());
                    response.setName(product.getName());
                    response.setSku(product.getSku());
                    response.setPrice(product.getPrice());
                    response.setQuantity(product.getQuantity());

                    return response;
                })
                .toList();
    }

    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with id: " + id
                ));

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setSku(product.getSku());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());

        return response;
    }

    public ProductResponse updateProduct(Long id, CreateProductRequest request) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with id: " + id
                ));

        if (productRepository.existsBySkuAndIdNot(request.getSku(), id)) {
            throw new DuplicateSkuException(
                    "Product with SKU " + request.getSku() + " already exists"
            );
        }

        existingProduct.setName(request.getName());
        existingProduct.setSku(request.getSku());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setQuantity(request.getQuantity());

        Product updatedProduct = productRepository.save(existingProduct);

        ProductResponse response = new ProductResponse();

        response.setId(updatedProduct.getId());
        response.setName(updatedProduct.getName());
        response.setSku(updatedProduct.getSku());
        response.setPrice(updatedProduct.getPrice());
        response.setQuantity(updatedProduct.getQuantity());

        return response;
    }

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with id: " + id
                ));

        productRepository.deleteById(id);
    }
}
