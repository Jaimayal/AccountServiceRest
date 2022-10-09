package com.jaimayal.accountservice.business.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email already registered")
public class EmailAlreadyRegisteredException extends RuntimeException {
}
