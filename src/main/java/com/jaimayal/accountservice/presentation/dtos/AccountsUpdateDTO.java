package com.jaimayal.accountservice.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jaimayal.accountservice.presentation.dtos.constraints.ValidAccounts;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@PropertySource(value = "classpath:messages.properties")
public class AccountsUpdateDTO {
    @JsonProperty(value = "accounts")
    @NotEmpty(message = "{accountsupdate.accounts.notempty}")
    @UniqueElements(message = "{accountsupdate.accounts.uniqueelements}")
    @ValidAccounts(message = "{accountsupdate.accounts.validaccounts}")
    private List<String> accounts;
    
    @JsonProperty(value = "operation")
    @NotBlank(message = "{accountsupdate.operation.notblank}")
    @Pattern(regexp = "^(REMOVE|ADD)$", message = "{accountsupdate.operation.pattern}")
    private String operation;
}
