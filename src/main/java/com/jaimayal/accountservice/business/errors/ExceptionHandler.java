package com.jaimayal.accountservice.business.errors;

import com.jaimayal.accountservice.business.errors.messages.ApiErrorMessage;
import com.jaimayal.accountservice.business.errors.messages.ValidationErrorMessage;
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
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, 
                                                                  HttpHeaders headers, 
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ValidationErrorMessage validationError = new ValidationErrorMessage(status, request, exception);
        return new ResponseEntity<>(validationError, headers, status);
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, 
                                                                         HttpHeaders headers, 
                                                                         HttpStatus status, 
                                                                         WebRequest request) {
        ApiErrorMessage ApiErrorMessage = new ApiErrorMessage(status, request, ex.getMessage());
        return new ResponseEntity<>(ApiErrorMessage, headers, status);
    }
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(
                status, 
                request, 
                "This endpoint does not exist since no handler was found");
        return new ResponseEntity<>(apiErrorMessage, headers, status);
    }
}
