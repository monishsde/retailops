package com.retailops.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(
            description = "Name of the product",
            example = "Laptop"
    )
    @NotBlank(message = "Product name is required")
    private String name;


    @Schema(
            description = "Unique Stock Keeping Unit identifier for the product",
            example = "LAP-101"
    )
    @NotBlank(message = "Product SKU is required")
    private String sku;


    @Schema(
            description = "Selling price of the product",
            example = "55000.00"
    )
    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be greater than Zero")
    private BigDecimal price;


    @Schema(
            description = "Available quantity of the product in inventory",
            example = "10"
    )
    @NotNull(message = "Product quantity is required")
    @PositiveOrZero(message = "Product quantity cannot be negative")
    private Integer quantity;
}
