package com.zubi.ecommerce.common.enums;

public enum ErrorCode {
    CUSTOM_ERROR_MESSAGE("1234", "{0}"),
    REQUEST_MALFORMED("1235", "Request is malformed."),

    VALIDATION_ERROR("6789", "Validation failed");

    private String message;
    private String code;

    ErrorCode(final String code, final String message){
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
