package com.zubi.ecommerce.common.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

    private String errorMessage;
    private String errorCode;
    private String debugMessage;
}
