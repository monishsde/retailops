package com.retailops.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product SKU is required")
    private String sku;

    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be greater than Zero")
    private BigDecimal price;

    @NotNull(message = "Product quantity is required")
    @PositiveOrZero(message = "Product quantity cannot be negative")
    private Integer quantity;
}
