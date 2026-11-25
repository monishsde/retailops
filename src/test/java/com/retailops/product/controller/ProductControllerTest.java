
package com.retailops.product.controller;

import com.retailops.product.dto.CreateProductRequest;
import com.retailops.product.dto.ProductResponse;
import com.retailops.product.service.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    // Helper method to prepare a sample response
    private ProductResponse createSampleProduct() {

        ProductResponse response = new ProductResponse();
        response.setId(1L);
        response.setName("iPhone 15");
        response.setSku("IP15");
        response.setPrice(new BigDecimal("65000"));
        response.setQuantity(10);

        return response;
    }

    // 1. Successful product creation
    @Test
    void shouldCreateProductSuccessfully() throws Exception {

        ProductResponse response = createSampleProduct();

        when(productService.createProduct(any(CreateProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "iPhone 15",
                                    "sku": "IP15",
                                    "price": 65000,
                                    "quantity": 10
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("iPhone 15"))
                .andExpect(jsonPath("$.sku").value("IP15"));
    }

    // 2. Reject blank product name
    @Test
    void shouldRejectProductWithBlankName() throws Exception {

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "",
                                    "sku": "IP15",
                                    "price": 65000,
                                    "quantity": 10
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    // 3. Reject missing product price
    @Test
    void shouldRejectProductWithMissingPrice() throws Exception {

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "iPhone 15",
                                    "sku": "IP15",
                                    "quantity": 10
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    // 4. Reject negative product quantity
    @Test
    void shouldRejectProductWithNegativeQuantity() throws Exception {

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "iPhone 15",
                                    "sku": "IP15",
                                    "price": 65000,
                                    "quantity": -1
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    // 5. Get all products
    @Test
    void shouldGetAllProducts() throws Exception {

        ProductResponse response = createSampleProduct();

        Page<ProductResponse> page = new PageImpl<>(
                List.of(response),
                PageRequest.of(0, 10),
                1
        );

        when(productService.getAllProducts(any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("iPhone 15"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    // 6. Get product by ID
    @Test
    void shouldGetProductById() throws Exception {

        ProductResponse response = createSampleProduct();

        when(productService.getProductById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("iPhone 15"));
    }

    // 7. Update product successfully
    @Test
    void shouldUpdateProductSuccessfully() throws Exception {

        ProductResponse response = createSampleProduct();

        when(productService.updateProduct(
                eq(1L),
                any(CreateProductRequest.class)
        )).thenReturn(response);

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "iPhone 15",
                                    "sku": "IP15",
                                    "price": 65000,
                                    "quantity": 15
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("iPhone 15"))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    // 8. Delete product successfully
    @Test
    void shouldDeleteProductSuccessfully() throws Exception {

        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(1L);
    }
}
