package com.retailops.product.service;


import com.retailops.common.exception.DuplicateSkuException;
import com.retailops.common.exception.ProductNotFoundException;
import com.retailops.product.dto.CreateProductRequest;
import com.retailops.product.dto.ProductResponse;
import com.retailops.product.entity.Product;
import com.retailops.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;


    @Test
    void shouldCreateProductSuccessfully() {

        // 1. Prepare input
        CreateProductRequest request = new CreateProductRequest();
        request.setName("iPhone 15");
        request.setSku("IP15");
        request.setPrice(new BigDecimal("65000"));
        request.setQuantity(10);

        // 2. Tell Mockito how the fake repository should behave
        when(productRepository.existsBySku("IP15"))
                .thenReturn(false);

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("iPhone 15");
        savedProduct.setSku("IP15");
        savedProduct.setPrice(new BigDecimal("65000"));
        savedProduct.setQuantity(10);

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        // 3. Execute the actual service method
        ProductResponse response = productService.createProduct(request);

        // 4. Verify the result
        assertEquals(1L, response.getId());
        assertEquals("iPhone 15", response.getName());
        assertEquals("IP15", response.getSku());
        assertEquals(new BigDecimal("65000"), response.getPrice());
        assertEquals(10, response.getQuantity());

        // 5. Verify that save() was called
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenSkuAlreadyExists() {

        // 1. Prepare input
        CreateProductRequest request = new CreateProductRequest();
        request.setName("iPhone 15");
        request.setSku("IP15");
        request.setPrice(new BigDecimal("65000"));
        request.setQuantity(10);

        // 2. Tell Mockito that this SKU already exists
        when(productRepository.existsBySku("IP15"))
                .thenReturn(true);

        // 3. Execute the method and expect an exception
        DuplicateSkuException exception = assertThrows(
                DuplicateSkuException.class,
                () -> productService.createProduct(request)
        );

        // 4. Verify the exception message
        assertEquals(
                "Product with SKU IP15 already exists",
                exception.getMessage()
        );

        // 5. Verify that the product was NOT saved
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        // 1. Tell Mockito that product ID 999 doesn't exist
        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        // 2. Call the service and expect an exception
        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(999L)
        );

        // 3. Verify the exception message
        assertEquals(
                "Product not found with id: 999",
                exception.getMessage()
        );
    }

}
