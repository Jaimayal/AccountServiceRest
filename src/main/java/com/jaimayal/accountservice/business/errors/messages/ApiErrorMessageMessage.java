package com.jaimayal.accountservice.business.errors.messages;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

@Getter
@Setter
public class ApiErrorMessageMessage extends GlobalErrorMessage {
    private String message;

    public ApiErrorMessageMessage(HttpStatus status, WebRequest request, String message) {
        super(status, request);
        this.message = message;
    }
}
