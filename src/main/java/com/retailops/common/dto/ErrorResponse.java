package com.retailops.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    @Schema(
            description = "Detailed description of the error",
            example = "Product not found with id: 999"
    )
    private String message;

    @Schema(
            description = "HTTP status code associated with the error",
            example = "404"
    )
    private int status;

    @Schema(
            description = "HTTP error reason phrase",
            example = "Not Found"
    )
    private String error;
}
