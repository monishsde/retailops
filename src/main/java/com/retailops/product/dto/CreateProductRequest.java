package com.retailops.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    private String name;
    private String sku;
    private BigDecimal price;
    private Integer quantity;
}
