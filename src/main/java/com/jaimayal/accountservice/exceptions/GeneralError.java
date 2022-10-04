package com.jaimayal.accountservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Getter
@Setter
public class GeneralError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;
    
    public GeneralError(HttpStatus status, WebRequest request) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.path = ((ServletWebRequest)request).getRequest().getRequestURI();
    }
}
