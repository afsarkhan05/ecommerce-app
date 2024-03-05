package com.zubi.ecommerce.common.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.zubi.ecommerce.common.enums.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpRequestNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus httpStatus,
            WebRequest request
    ){
        String debugMessage = ex.getLocalizedMessage();
        List<ApiError> errors = new ArrayList<>();
        if(ex.getCause() !=null && ex.getCause() instanceof InvalidFormatException){

            ApiError apiError = ApiError.builder()
                    .errorCode(ErrorCode.REQUEST_MALFORMED.getCode())
                    .errorMessage(ErrorCode.REQUEST_MALFORMED.getMessage())
                   // .debugMessage(debugMessage)
                    .build();
            errors.add(apiError);
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<List<ApiError>> handleCustomException(CustomException ex) {
        // List<String> errorMessages = new ArrayList<>();
        List<ApiError> errors = new ArrayList<>();
        errors.add(ApiError.builder()
                .errorMessage(ex.getMessage())
                .errorCode(ex.getErrorCode())
                //.debugMessage(ex.getLocalizedMessage())
                .build());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ApiError>> handleConstraintViolationException(ConstraintViolationException ex) {
       // List<String> errorMessages = new ArrayList<>();
        List<ApiError> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(ApiError.builder()
                    .errorMessage(violation.getMessage())
                    .errorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                    //.debugMessage(ex.getLocalizedMessage())
                    .build());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleInternalServerError(Exception ex){
        List<ApiError> errors = new ArrayList<>();
        log.error(ExceptionUtils.getStackTrace(ex));
        errors.add(ApiError.builder()
                        .errorMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .errorCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        //.debugMessage(ex.getLocalizedMessage())
                        .build());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }
}
