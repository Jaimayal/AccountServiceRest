package com.jaimayal.accountservice.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EndpointError extends ApiError {
    private String message;
}
