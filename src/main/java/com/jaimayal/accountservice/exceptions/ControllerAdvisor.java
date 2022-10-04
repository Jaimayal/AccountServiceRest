package com.jaimayal.accountservice.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, 
                                                                  HttpHeaders headers, 
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(LocalDateTime.now());
        validationError.setStatus(status.value());
        validationError.setError(status.getReasonPhrase());
        
        List<String> messages = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        validationError.setMessages(messages);
        validationError.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
        
        return new ResponseEntity<>(validationError, headers, status);
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, 
                                                                         HttpHeaders headers, 
                                                                         HttpStatus status, 
                                                                         WebRequest request) {
        EndpointError endpointError = new EndpointError();
        endpointError.setTimestamp(LocalDateTime.now());
        endpointError.setStatus(status.value());
        endpointError.setError(status.getReasonPhrase());
        endpointError.setMessage(ex.getMessage());
        endpointError.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
        
        return new ResponseEntity<>(endpointError, headers, status);
    }
}
