package com.jaimayal.accountservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class PasswordUpdateDTO {
    @JsonProperty(value = "oldPassword")
    @NotBlank(message = "oldPassword is required and it must not be blank")
    @Size(min = 8, max = 30, message = "oldPassword must be within 8 and 30 chars long")
    @Pattern(regexp = "^[\\w-.,]+$",
            message = "oldPassword must contain only letters, digits and _ - . , symbols")
    private String oldPassword;
    
    @JsonProperty(value = "newPassword")
    @NotBlank(message = "newPassword is required and it must not be blank")
    @Size(min = 8, max = 30, message = "newPassword must be within 8 and 30 chars long")
    @Pattern(regexp = "^[\\w-.,]+$",
            message = "newPassword must contain only letters, digits and _ - . , symbols")
    private String newPassword;
}
