package com.jaimayal.accountservice.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jaimayal.accountservice.presentation.dtos.constraints.ValidAccounts;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
public class AccountsUpdateDTO {
    @JsonProperty(value = "accounts")
    @NotEmpty(message = "accounts is required and it must not be empty")
    @UniqueElements(message = "accounts must contain only unique elements")
    @ValidAccounts(message = "accounts must contain only popular social media accounts " +
            "(facebook, instagram, twitter, reddit, snapchat and / or tiktok)")
    private List<String> accounts;
    
    @JsonProperty(value = "operation")
    @NotBlank(message = "operation is required and it must not be blank")
    @Pattern(regexp = "^(REMOVE|ADD)$", message = "operation must be REMOVE or ADD")
    private String operationType;
}
