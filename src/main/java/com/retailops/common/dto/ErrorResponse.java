package com.retailops.common.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private int status;
    private String error;
}
