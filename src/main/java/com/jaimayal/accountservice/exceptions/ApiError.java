package com.jaimayal.accountservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Getter
@Setter
public class ApiError extends GeneralError {
    private String message;

    public ApiError(HttpStatus status, WebRequest request, String message) {
        super(status, request);
        this.message = message;
    }
}
