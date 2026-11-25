package com.retailops.product.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateProductRequestTest {


    @Test
    void shouldRejectBlankProductName() {

        // 1. Create an invalid request
        CreateProductRequest request = new CreateProductRequest();
        request.setName("");
        request.setSku("IP15");
        request.setPrice(new java.math.BigDecimal("65000"));
        request.setQuantity(10);

        // 2. Create a validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // 3. Validate the request
        var violations = validator.validate(request);

        // 4. Verify that validation failed
        assertEquals(1, violations.size());
        assertEquals("Product name is required", violations.iterator().next().getMessage());

        factory.close();
    }

    @Test
    void shouldRejectNegativeProductPrice() {

        // 1. Create an invalid request
        CreateProductRequest request = new CreateProductRequest();
        request.setName("iPhone 15");
        request.setSku("IP15");
        request.setPrice(new java.math.BigDecimal("-100"));
        request.setQuantity(10);

        // 2. Create a validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // 3. Validate the request
        var violations = validator.validate(request);

        // 4. Verify the exact validation error
        assertEquals(1, violations.size());
        assertEquals("Product price must be greater than Zero", violations.iterator().next().getMessage());

        factory.close();
    }

    @Test
    void shouldRejectNegativeProductQuantity() {

        // 1. Create an invalid request
        CreateProductRequest request = new CreateProductRequest();
        request.setName("iPhone 15");
        request.setSku("IP15");
        request.setPrice(new java.math.BigDecimal("100"));
        request.setQuantity(-1);

        // 2. Create a validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // 3. Validate the request
        var violations = validator.validate(request);

        // 4. Verify the exact validation error
        assertEquals(1, violations.size());
        assertEquals("Product quantity cannot be negative", violations.iterator().next().getMessage());

        factory.close();
    }

    @Test
    void shouldRejectZeroProductPrice() {

        CreateProductRequest request = new CreateProductRequest();
        request.setName("iPhone 15");
        request.setSku("IP15");
        request.setPrice(new java.math.BigDecimal("0"));
        request.setQuantity(10);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        var violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertEquals(
                "Product price must be greater than Zero",
                violations.iterator().next().getMessage()
        );

        factory.close();
    }

    }
