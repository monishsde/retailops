package com.retailops.product.service;


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

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

}
