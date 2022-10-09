package com.jaimayal.accountservice.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@PropertySource(value = "classpath:messages.properties")
public class PasswordUpdateDTO {
    @JsonProperty(value = "oldPassword")
    @NotBlank(message = "{passwordupdate.oldpassword.notblank}")
    @Size(min = 8, max = 30, message = "{passwordupdate.oldpassword.size}")
    @Pattern(regexp = "^[\\w-.,]+$",
            message = "{passwordupdate.oldpassword.pattern}")
    private String oldPassword;
    
    @JsonProperty(value = "newPassword")
    @NotBlank(message = "{passwordupdate.newpassword.notblank}")
    @Size(min = 8, max = 30, message = "{passwordupdate.newpassword.size}")
    @Pattern(regexp = "^[\\w-.,]+$",
            message = "{passwordupdate.newpassword.pattern}")
    private String newPassword;
}
