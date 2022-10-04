package com.jaimayal.accountservice.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public abstract class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    
    private String path;
}
