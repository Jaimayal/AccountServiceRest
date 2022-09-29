package com.jaimayal.accountservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The provided old password does not match")
public class PasswordDoesNotMatchException extends RuntimeException {
}
