package com.zubi.ecommerce.common.exceptions;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private String errorCode;

    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}