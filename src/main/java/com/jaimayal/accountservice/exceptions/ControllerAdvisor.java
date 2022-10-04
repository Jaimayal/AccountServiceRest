package com.jaimayal.accountservice.exceptions;

import com.jaimayal.accountservice.error_messages.ApiError;
import com.jaimayal.accountservice.error_messages.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, 
                                                                  HttpHeaders headers, 
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ValidationError validationError = new ValidationError(status, request, exception);
        return new ResponseEntity<>(validationError, headers, status);
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, 
                                                                         HttpHeaders headers, 
                                                                         HttpStatus status, 
                                                                         WebRequest request) {
        ApiError ApiError = new ApiError(status, request, ex.getMessage());
        return new ResponseEntity<>(ApiError, headers, status);
    }
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        ApiError apiError = new ApiError(status, request, "This endpoint does not exist since no handler was found");
        return new ResponseEntity<>(apiError, headers, status);
    }
}
