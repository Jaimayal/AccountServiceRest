package com.jaimayal.accountservice.business.errors.messages;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class ValidationErrorMessage extends GlobalErrorMessage {
    private List<String> messages;
    
    public ValidationErrorMessage(HttpStatus status, WebRequest request,
                                  MethodArgumentNotValidException exception) {
        super(status, request);
        this.messages = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
