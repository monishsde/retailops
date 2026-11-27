package com.retailops.product.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {

    @Schema(
            description = "Unique database-generated product ID",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Name of the product",
            example = "Laptop"
    )
    private String name;


    @Schema(
            description = "Unique Stock Keeping Unit identifier",
            example = "LAP-101"
    )
    private String sku;

    @Schema(
            description = "Selling price of the product",
            example = "55000.00"
    )
    private BigDecimal price;

    @Schema(
            description = "Available quantity of the product",
            example = "10"
    )
    private Integer quantity;
}
